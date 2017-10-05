package dtu.is31380.busPass;

import java.util.Collection;

import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * This is a sample class to launch a rule.
 */
public class BusPassTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
        	
            kSession.insert(new Person("Jorn",21,"London"));
            kSession.insert(new Person("Maggie",14,"Copenhagen"));
            FactHandle bj = kSession.insert(new Person("Bjoern",15,"London"));
            kSession.insert(new Person("Anna",31,"London"));

            //kSession.fireAllRules();
            
            Collection<FactHandle> facts = kSession.getFactHandles();
            int i =0;
            for (FactHandle fact : facts) {
            	 i+=1;
            	System.out.println(1+": "+fact.toString());
            }
            
            // change some input facts
            kSession.update(bj, new Person("Bjoern",16,"London"));
            kSession.update(bj, new Person("Bjarke",40,"Manchester"));
            
            // and fire it again
            //kSession.fireAllRules();
            kSession.fireAllRules(new RuleNameStartsWithAgendaFilter("Infer"));
            kSession.fireAllRules(new RuleNameStartsWithAgendaFilter("Issue "));
            kSession.fireAllRules(new RuleNameStartsWithAgendaFilter("Return "));
            
                        
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

 
}
