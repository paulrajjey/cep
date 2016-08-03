package com.sample;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.audit.WorkingMemoryInMemoryLogger;
import org.drools.audit.event.LogEvent;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
    	
    	
    	multihreadSession();
        /*try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            AgentaEventListener listener = new  AgentaEventListener();
           // ksession.addEventListener(listener);
            //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
            WorkingMemoryInMemoryLogger wmlogger = new WorkingMemoryInMemoryLogger(ksession);
            
            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            ksession.insert(message);
            ksession.fireAllRules();
            List<LogEvent> logevents = wmlogger.getLogEvents();
            for (LogEvent event : logevents){
            	event.getType();
            }
            String evets = wmlogger.getEvents();
            System.out.println(evets);
            //logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }*/
    }
   
    public static void multihreadSession(){
    	try {
			
    		final KnowledgeBase kbase = readKnowledgeBase();
		
    	
    	Runnable session = new Runnable() {
    		public void run(){
    			 
    			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    			 
    			  // go !
    	            Message message = new Message();
    	            message.setMessage("Hello World");
    	            message.setStatus(Message.HELLO);
    	            ksession.insert(message);
    	            ksession.fireAllRules();
    		}
    	};
    	
    	for ( int i=1 ; i <=4; i++){
    		System.out.println("thread - " + i);
    		
    		Thread t = new Thread(session);
    		t.sleep(1000);
    		t.start();
    	}
    	System.out.println("jey hold");
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
    public static class MessageText {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        public MessageText(String message, int status) {
			super();
			this.message = message;
			this.status = status;
		}

		public MessageText() {
			super();
			// TODO Auto-generated constructor stub
		}

		private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}
