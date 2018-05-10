package projtranspalim;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author billy
 */
public class Roteador extends Agent{
	private MessageTemplate template = MessageTemplate.and(
		MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),
		MessageTemplate.MatchOntology("presenca") );
		
	protected void setup() {          
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive(template);
				if (msg != null) {
					System.out.println("Recebido QUERY_IF de "+msg.getSender().getName());
					ACLMessage reply = msg.createReply();
					if ("vivo".equals(msg.getContent())) {
						reply.setPerformative(ACLMessage.INFORM);
						reply.setContent("vivo");
					}
					else {
						reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
						reply.setContent("mess-desconhecida");
					}
					myAgent.send(reply);
				}
				else {
					block();
				}
			}
		} );
	}
}
