package projtranspalim;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author billy
 */
public class Carregador extends Agent{		
	protected void setup() {          
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					System.out.println("==(Carregador) Mensagem <- "+msg.getContent()+" de " +msg.getSender().getName());
					ACLMessage reply = msg.createReply();
					if (null == msg.getContent()) {
                                            reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                                            reply.setContent("mens-desconhecida");
                                        }
					if(msg.getContent().startsWith("vivo")) {
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                        }
                                        else if(msg.getContent().startsWith("iniciar")) {
                                            System.out.println("Agente Carregador iniciado.");
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                        }
                                        else if("true".equals(msg.getContent())) {
                                            System.out.println("==(Carregador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
                                        }
                                        else {
                                            reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                                            reply.setContent("mens-desconhecida");                                            
                                        }                                    
					myAgent.send(reply);
				}
				else {
					block();
				}
			}
		} );
	}
        
        protected void sendMessage(String content, String ontology, String agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(content);
        msg.setProtocol("fipa-request");
        msg.setOntology(ontology);
        AID ag = new AID(agent, AID.ISLOCALNAME);
        msg.addReceiver(ag);
        send(msg);
        System.out.println("==(Carregador) Mensagem -> " + msg.getContent() + " para " + ag.getLocalName());
    }
}
