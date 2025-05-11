package com.pet.manager.service.impl;

import com.pet.manager.config.ModelProperties;
import com.pet.manager.service.IAiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Service
public class AiServiceImpl implements IAiService {
    private final InMemoryChatMemory chatMemory = new InMemoryChatMemory();
    private static final String SYSTEM_ROLE = """
        你是一名资深犬类皮肤病专家，需要根据用户提供的症状描述：
        1. 给出专业诊断建议（分点列出，用数字序号标注）
        2. 建议必须包含【病因分析】【护理方案】【用药指导】三个板块
        3. 使用纯文本格式，禁止使用Markdown
        4. 语言简洁专业，不超过500字
        5. 如果所给出的message为空，就输出识别失败，并给出去专业宠物医院的建议""";

    private final ChatClient deepSeekChatClient;
    private final ChatClient qwenChatClient;
    private final ChatClient zhipuChatClient;

    @Autowired
    public AiServiceImpl(ModelProperties modelProperties) {
        // DeepSeek（使用openai配置项）
        this.deepSeekChatClient = buildChatClient(
                modelProperties.getOpenai().getBaseUrl(),
                modelProperties.getOpenai().getApiKey(),
                modelProperties.getOpenai().getChat().getOptions()
        );

        // Qwen（使用dashscope配置项）
        this.qwenChatClient = buildChatClient(
                modelProperties.getDashscope().getBaseUrl(),
                modelProperties.getDashscope().getApiKey(),
                modelProperties.getDashscope().getChat().getOptions()
        );

        // ZhipuAI（使用zhipuai配置项）
        this.zhipuChatClient = buildChatClient(
                modelProperties.getZhipuai().getBaseUrl(),
                modelProperties.getZhipuai().getApiKey(),
                modelProperties.getZhipuai().getChat().getOptions()
        );
    }

    private ChatClient buildChatClient(String baseUrl, String apiKey, ModelProperties.Options options) {
        OpenAiApi api = new OpenAiApi(baseUrl, apiKey);

        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model(options.getModel())
                .temperature(options.getTemperature())
                .maxTokens(options.getMaxTokens())
                .topP(0.95)
                .stop(Arrays.asList("#", "**", "```"))
                .build();

        return ChatClient.builder(new OpenAiChatModel(api, chatOptions))
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .defaultSystem(SYSTEM_ROLE)
                .build();
    }

    @Override
    public String chat(String message, String modelName, String chatId) {
        ChatClient chatClient = switch (modelName) {
            case "0" -> zhipuChatClient;
            case "1" -> qwenChatClient;
            case "2" -> deepSeekChatClient;
            default -> throw new IllegalArgumentException("未知模型标识: " + modelName);
        };

        ChatClient.ChatClientRequestSpec request = chatClient.prompt(message);
        if (chatId != null) {
            request.advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId));
        }
        return request.call().content();
    }

    @Override
    public Flux<String> chatStream(String message, String modelName, String chatId) {
        ChatClient chatClient = getChatClient(modelName);
        ChatClient.ChatClientRequestSpec request = chatClient.prompt(message);
        if (chatId != null) {
            request.advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId));
        }
        return request.stream().content();
    }

    private ChatClient getChatClient(String modelName) {
        switch (modelName) {
            case "0":
                return zhipuChatClient;
            case "1":
                return qwenChatClient;
            case "2":
                return deepSeekChatClient;
            default:
                throw new IllegalArgumentException("Unknown model identifier: " + modelName);
        }
    }

    public String chatWithZhipu(String message, String chatId) {
        ChatClient.ChatClientRequestSpec request = zhipuChatClient.prompt(message);
        if (chatId != null) {
            request.advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId));
        }
        return request.call().content();
    }

    public String chatWithQwen(String message, String chatId) {
        ChatClient.ChatClientRequestSpec request = qwenChatClient.prompt(message);
        if (chatId != null) {
            request.advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId));
        }
        return request.call().content();
    }

    public String chatWithDeepSeek(String message, String chatId) {
        ChatClient.ChatClientRequestSpec request = deepSeekChatClient.prompt(message);
        if (chatId != null) {
            request.advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId));
        }
        return request.call().content();
    }
}
