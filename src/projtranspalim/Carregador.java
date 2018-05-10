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
                                            myAgent.send(reply);
                                        }
					if("vivo".equals(msg.getContent())) {
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                            myAgent.send(reply);
                                        }
                                        else if("iniciar".equals(msg.getContent())) {
                                            System.out.println("Agente Carregador iniciado.");
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                            myAgent.send(reply);
                                        }
                                        else if("true".equals(msg.getContent())) {
                                            System.out.println("==(Carregador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
                                        }
                                        else {
                                            reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                                            reply.setContent("mens-desconhecida");      
                                            myAgent.send(reply);
                                        } 					
				}
				else {
					block();
				}
			}
		} );
	}

}
