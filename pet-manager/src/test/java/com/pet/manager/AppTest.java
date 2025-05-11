//package com.pet.manager;
//
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//import org.jetbrains.annotations.TestOnly;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Unit test for simple App.
// */
//@SpringBootTest
//@Configuration
//public class AppTest
//    extends TestCase
//{
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public AppTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
//
//    @Autowired
//    private ChatLanguageModel chatLanguageModel;
//
//    public void testChat(){
//        String generate = chatLanguageModel.generate("你好");
//
//        System.out.println(generate);
//    }
//}
