package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.drools.event.rule.ActivationCancelledCause;
import org.drools.event.rule.ActivationCancelledEvent;
import org.drools.event.rule.ActivationCreatedEvent;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.AgendaGroupPoppedEvent;
import org.drools.event.rule.AgendaGroupPushedEvent;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;


public class AgentaEventListener extends DefaultAgendaEventListener {
	final Logger logger  = Logger.getLogger(this.getClass());
	List<String> firedRuleList = new ArrayList<String>();

	@Override
	public void activationCancelled(ActivationCancelledEvent event) {
		// TODO Auto-generated method stub
		super.activationCancelled(event);
		ActivationCancelledCause ac = event.getCause();
		System.out.println("cause" + ac.name());
		System.out.println("");
	}

	@Override
	public void activationCreated(ActivationCreatedEvent event) {
		// TODO Auto-generated method stub
		super.activationCreated(event);
		List<String> decl =  event.getActivation().getDeclarationIDs();
		for (String arg0 : decl){
			Object obj = event.getActivation().getDeclarationValue(arg0);
			
			System.out.println("decleration name " + obj.getClass().getSimpleName());
			
		}
	}

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event) {
		String packageName = event.getActivation().getRule().getPackageName();
		String rulename = event.getActivation().getRule().getName();
		//System.out.println("Rule fired --->" +rulename );
		firedRuleList.add("Package ( " + packageName + " ) " + "Rule (" + rulename + " )" );
		
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
		// TODO Auto-generated method stub
		super.agendaGroupPopped(event);
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
		// TODO Auto-generated method stub
		super.agendaGroupPushed(event);
	}

	@Override
	public void beforeActivationFired(BeforeActivationFiredEvent event) {
		// TODO Auto-generated method stub
		super.beforeActivationFired(event);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(logger.isDebugEnabled()){
			logger.debug("-------------Fired Rules-------------");
			//System.out.println("-------------Fired Rules-------------");
		}
		for (String firedRule : firedRuleList ){
			
			if(logger.isDebugEnabled()){
				logger.debug("Rule information ----> "+firedRule);
				//System.out.println("Rule information ----> "+firedRule);
			}
		}
		if(logger.isDebugEnabled()){
			/*System.out.println("--------------------------------------------------------------------" );
			System.out.println("total number of rules fired----->>" + firedRuleList.size());
			System.out.println("-------------Fired Rules---------------------------------------------");
			System.out.println("--------------------------------------------------------------------" );
			*/
			logger.debug("-------------Fired Rules---------------------------------------------");
			logger.debug("total number of rules fired-----> " + firedRuleList.size());
			logger.debug("-------------Fired Rules---------------------------------------------");
			
		}
		return "";
	}
	
	public boolean isRuleFired(String ruleName){
		boolean retBoolean =false;
		for (String firedRule :firedRuleList) {
			if(ruleName.equals(firedRule)){
				retBoolean= true;
			}
			
		}
		return retBoolean;
		
	}
	public void clear(){
		firedRuleList.clear();
	}
	
}
