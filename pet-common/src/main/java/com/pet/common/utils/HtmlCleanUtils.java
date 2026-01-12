package com.pet.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashSet;
import java.util.Set;

/**
 * HTML标签清理工具类
 * 提供多种HTML清理和过滤功能
 */
public class HtmlCleanUtils {

    /**
     * 基础HTML标签清理（使用Jsoup）
     * @param html HTML内容
     * @param allowTags 允许的标签，为空则不允许任何HTML标签
     * @return 清理后的文本
     */
    public static String cleanHtml(String html, String... allowTags) {
        if (html == null) {
            return "";
        }

        if (allowTags == null || allowTags.length == 0) {
            // 不允许任何HTML标签，只保留纯文本
            return Jsoup.clean(html, Safelist.none());
        }

        // 创建自定义Safelist
        Safelist safelist = Safelist.simpleText();
        for (String tag : allowTags) {
            safelist.addTags(tag);

            // 为特定标签添加允许的属性
            switch (tag.toLowerCase()) {
                case "a":
                    safelist.addAttributes("a", "href", "title", "target");
                    safelist.addProtocols("a", "href", "http", "https", "mailto");
                    break;
                case "img":
                    safelist.addAttributes("img", "src", "alt", "title", "width", "height");
                    safelist.addProtocols("img", "src", "http", "https");
                    break;
                case "font":
                case "span":
                    safelist.addAttributes(tag, "style", "color", "size");
                    break;
            }
        }

        return Jsoup.clean(html, safelist);
    }

    /**
     * 移除所有HTML标签，保留纯文本
     */
    public static String stripHtml(String html) {
        if (html == null) {
            return "";
        }

        // 使用Jsoup的none safelist
        String clean = Jsoup.clean(html, Safelist.none());

        // 额外处理HTML实体
        clean = clean.replaceAll("&nbsp;", " ")
                .replaceAll("&amp;", "&")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&quot;", "\"")
                .replaceAll("&#39;", "'");

        // 合并多个空白字符
        clean = clean.replaceAll("\\s+", " ").trim();

        return clean;
    }

    /**
     * 移除指定HTML标签
     * @param html HTML内容
     * @param tagsToRemove 要移除的标签名
     */
    public static String removeSpecificTags(String html, String... tagsToRemove) {
        if (html == null || tagsToRemove == null || tagsToRemove.length == 0) {
            return html;
        }

        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("</?(");

        for (int i = 0; i < tagsToRemove.length; i++) {
            if (i > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append(Pattern.quote(tagsToRemove[i]));
        }

        patternBuilder.append(")(\\s[^>]*)?>");

        Pattern pattern = Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        return matcher.replaceAll("");
    }

    /**
     * 移除HTML标签但保留换行格式
     */
    public static String stripHtmlKeepLineBreaks(String html) {
        if (html == null) {
            return "";
        }

        // 将<br>、<p>等标签转换为换行符
        html = html.replaceAll("(?i)<br\\s*/?>", "\n")
                .replaceAll("(?i)</p>", "\n")
                .replaceAll("(?i)</div>", "\n")
                .replaceAll("(?i)</tr>", "\n")
                .replaceAll("(?i)<li>", "\n• ");

        // 移除所有HTML标签
        html = stripHtml(html);

        // 标准化换行符
        html = html.replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        // 合并多个连续换行
        html = html.replaceAll("\n{3,}", "\n\n");

        return html.trim();
    }

    /**
     * 移除HTML中的JavaScript和CSS
     */
    public static String removeScriptAndStyle(String html) {
        if (html == null) {
            return "";
        }

        // 移除<script>标签及其内容
        html = Pattern.compile("<script[^>]*>.*?</script>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
                .matcher(html)
                .replaceAll("");

        // 移除<style>标签及其内容
        html = Pattern.compile("<style[^>]*>.*?</style>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
                .matcher(html)
                .replaceAll("");

        // 移除事件属性（onclick, onload等）
        html = Pattern.compile("\\s+on\\w+\\s*=\\s*[\"'][^\"']*[\"']", Pattern.CASE_INSENSITIVE)
                .matcher(html)
                .replaceAll("");

        // 移除javascript:协议
        html = html.replaceAll("(?i)javascript:", "");

        return html;
    }

    /**
     * 过滤危险的HTML内容（防XSS攻击）
     */
    public static String safeHtml(String html) {
        if (html == null) {
            return "";
        }

        // 使用Jsoup的relaxed配置，但移除危险属性
        Safelist safelist = Safelist.relaxed()
                .addProtocols("a", "href", "http", "https", "mailto")
                .addProtocols("img", "src", "http", "https")
                .addAttributes(":all", "class", "style", "title", "alt")
                .removeAttributes("a", "target")  // 移除target属性
                .removeTags("script", "iframe", "frame", "frameset", "object", "embed", "applet");

        // 移除所有事件属性
        html = removeEventAttributes(html);

        // 清理HTML
        Document dirty = Jsoup.parseBodyFragment(html, "");
        Cleaner cleaner = new Cleaner(safelist);
        Document clean = cleaner.clean(dirty);

        return clean.body().html();
    }

    /**
     * 移除所有事件属性
     */
    private static String removeEventAttributes(String html) {
        if (html == null) {
            return "";
        }

        // 常见的事件属性
        String[] events = {
                "onclick", "ondblclick", "onmousedown", "onmouseup", "onmouseover",
                "onmousemove", "onmouseout", "onkeypress", "onkeydown", "onkeyup",
                "onload", "onunload", "onabort", "onerror", "onfocus", "onblur",
                "onchange", "onsubmit", "onreset", "onselect", "onresize",
                "onscroll", "oncontextmenu", "oncopy", "oncut", "onpaste"
        };

        String result = html;
        for (String event : events) {
            String pattern = "\\s+" + event + "\\s*=\\s*[\"'][^\"']*[\"']";
            result = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
                    .matcher(result)
                    .replaceAll("");
        }

        return result;
    }

    /**
     * 提取HTML中的纯文本，限制最大长度
     */
    public static String extractText(String html, int maxLength) {
        if (html == null) {
            return "";
        }

        String text = stripHtml(html);

        if (text.length() > maxLength) {
            text = text.substring(0, maxLength) + "...";
        }

        return text;
    }

    /**
     * 获取HTML中的所有链接
     */
    public static Set<String> extractLinks(String html) {
        Set<String> links = new HashSet<>();

        if (html == null || html.isEmpty()) {
            return links;
        }

        Pattern pattern = Pattern.compile(
                "<a\\s+[^>]*href\\s*=\\s*[\"']([^\"']+)[\"'][^>]*>",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String link = matcher.group(1).trim();
            if (!link.isEmpty()) {
                links.add(link);
            }
        }

        return links;
    }

    /**
     * 移除HTML注释
     */
    public static String removeComments(String html) {
        if (html == null) {
            return "";
        }

        return html.replaceAll("<!--.*?-->", "");
    }

    /**
     * 压缩HTML（移除多余空白）
     */
    public static String compressHtml(String html) {
        if (html == null) {
            return "";
        }

        // 移除HTML注释
        html = removeComments(html);

        // 移除标签间的空白
        html = html.replaceAll(">\\s+<", "><");

        // 移除行首行尾空白
        html = html.replaceAll("^\\s+|\\s+$", "");

        // 合并多个空白字符
        html = html.replaceAll("\\s+", " ");

        return html;
    }

    /**
     * 检查是否包含HTML标签
     */
    public static boolean containsHtml(String text) {
        if (text == null) {
            return false;
        }

        Pattern htmlPattern = Pattern.compile("<[^>]+>");
        return htmlPattern.matcher(text).find();
    }

    /**
     * 转义HTML特殊字符
     */
    public static String escapeHtml(String text) {
        if (text == null) {
            return "";
        }

        return text.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;");
    }

    /**
     * 反转义HTML实体
     */
    public static String unescapeHtml(String text) {
        if (text == null) {
            return "";
        }

        return text.replaceAll("&amp;", "&")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&quot;", "\"")
                .replaceAll("&#39;", "'")
                .replaceAll("&nbsp;", " ");
    }

    /**
     * 清理富文本编辑器内容（保留基本格式）
     */
    public static String cleanRichText(String html) {
        if (html == null) {
            return "";
        }

        Safelist safelist = Safelist.basicWithImages()
                .addTags("div", "span", "font", "u", "s", "sub", "sup", "hr", "br")
                .addAttributes(":all", "style", "class", "id")
                .addAttributes("font", "color", "size", "face")
                .addAttributes("table", "border", "cellpadding", "cellspacing", "width", "height")
                .addAttributes("td", "colspan", "rowspan", "width", "height")
                .addAttributes("th", "colspan", "rowspan", "width", "height")
                .addAttributes("img", "src", "alt", "title", "width", "height", "style")
                .addProtocols("a", "href", "http", "https", "mailto", "ftp")
                .addProtocols("img", "src", "http", "https");

        // 移除危险内容
        html = removeScriptAndStyle(html);
        html = removeEventAttributes(html);

        return Jsoup.clean(html, safelist);
    }
}