/**
 * 
 */
package com.drools.cep;

import java.util.concurrent.TimeUnit;



import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.type.FactType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionPseudoClock;


public class Tester {

	private static final String PACKAGE_NAME = "com.drools.cep.test";
	private static final String DRL_FILENAME = "account.drl";
	private static final String ACC = "1234-65-AFD";

	private static final String ENTRY_POINT = "ClaimStream";
	private static final String ACC_INFO_OBJNAME = "AccountInfo";
	private static final String ACC_NUMBER_PROPNAME = "accountNumber";
	private static final String AVE_OFTTL_CLAIMS_PROPNAME = "aveOfLastClaims";
	private static final String AVE_FORPERIOD_PROPNAME = "aveForPeriod";
	private static final String BOOL_FORCLAIMS_PROPNAME = "eligibleForBonusClaims";
	private static final String BOOL_FORPERIOD_PROPNAME = "eligibleForPeriodicBonus";

	private static final double THRESHOLD = 70.0;

	private static Object accountInfo = null;
	private static FactType accountInfoFactType = null;
	private static KnowledgeBase knowledgeBase = null;
	private static StatefulKnowledgeSession session = null;
	private static Account account = null;

	private static void setUp() {
		prepareKnowledgeBase();
		prepareAccountInfo(knowledgeBase, ACC);
		prepareStatefulKnowledgeSession();
		prepareAccount();
	}

	private static void prepareAccount() {
		account = new Account();
		account.setNumber(ACC);
		account.setThreshold(THRESHOLD);
	}

	private static void prepareStatefulKnowledgeSession() {
		KnowledgeSessionConfiguration sessConfig = KnowledgeBaseFactory
				.newKnowledgeSessionConfiguration();
		sessConfig.setOption(ClockTypeOption.get("pseudo"));
		session = knowledgeBase.newStatefulKnowledgeSession(sessConfig, null);
	}

	private static void prepareKnowledgeBase() {
		try {

			KnowledgeBuilder builder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();
			builder.add(ResourceFactory.newClassPathResource(DRL_FILENAME),
					ResourceType.DRL);
			KnowledgeBuilderErrors errors = builder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError error : errors) {
					System.err.println(error);
				}
				throw new IllegalArgumentException("Could not parse knowledge.");
			}

			knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
			knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void prepareAccountInfo(KnowledgeBase knowledgeBase,
			String accountNumber) {
		try {
			accountInfoFactType = knowledgeBase.getFactType(PACKAGE_NAME,
					ACC_INFO_OBJNAME);
			accountInfo = accountInfoFactType.newInstance();
			accountInfoFactType.set(accountInfo, ACC_NUMBER_PROPNAME,
					accountNumber);
			accountInfoFactType.set(accountInfo, AVE_OFTTL_CLAIMS_PROPNAME,
					Double.MIN_VALUE);
			accountInfoFactType.set(accountInfo, AVE_FORPERIOD_PROPNAME,
					Double.MIN_VALUE);
			accountInfoFactType.set(accountInfo, BOOL_FORCLAIMS_PROPNAME,
					Boolean.FALSE);
			accountInfoFactType.set(accountInfo, BOOL_FORPERIOD_PROPNAME,
					Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		setUp();

		try {

			SessionPseudoClock clock = session.getSessionClock();

			WorkingMemoryEntryPoint claimStream = session
					.getWorkingMemoryEntryPoint(ENTRY_POINT);

			claimStream.insert(new ClaimApprovedEvent(ACC, 12.00));
			clock.advanceTime(80, TimeUnit.DAYS);

			claimStream.insert(new ClaimApprovedEvent(ACC, 46.00));
			clock.advanceTime(15, TimeUnit.DAYS);

			claimStream.insert(new ClaimApprovedEvent(ACC, 60.00));
			clock.advanceTime(45, TimeUnit.DAYS);

			claimStream.insert(new ClaimApprovedEvent(ACC, 110.00));
			clock.advanceTime(60, TimeUnit.DAYS);

			claimStream.insert(new ClaimApprovedEvent(ACC, 20.00));

			session.insert(account);
			session.insert(accountInfo);
			session.fireAllRules();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
