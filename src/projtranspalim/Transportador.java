package projtranspalim;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author billy
 */
public class Transportador extends Agent{	            
	protected void setup() {          
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					System.out.println("==(Transportador) Mensagem <- "+msg.getContent()+" de " +msg.getSender().getName());
					ACLMessage reply = msg.createReply();
		
					if(msg.getContent().startsWith("vivo")) {
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                        }
                                        else if(msg.getContent().startsWith("iniciar")) {
                                                System.out.println("=>(Transportador) Agente Transportador iniciado.");
                                                reply.setPerformative(ACLMessage.REQUEST);
                                                reply.setContent("true");
                                                pedirRotaRoteador();                                 
                                        }
                                        else if(msg.getContent().startsWith("hold")) {
                                            System.out.println("==(Transportador) recebeu uma resposta hold de " + msg.getSender().getName() + " e agora está aguardando resposta.");
                                        }
                                        else if(msg.getContent().startsWith("release")) {
                                            System.out.println("==(Transportador) recebeu um release de " +msg.getSender().getName()+" e agora está livre.");
                                        }
                                        else if(msg.getContent().startsWith("carregado")) {
                                            System.out.println("==(Transportador) foi carregado com as caixas e está a caminho.");                                            
                                            System.out.println("==(Transportador) O Transportador alcançou o final de sua rota.");
                                            informarDescarregador();
                                        }
                                        else if(msg.getContent().startsWith("rota")) {
                                            System.out.println("==(Transportador) recebeu a rota " + msg.getContent().split(" ")[1] + " do Roteador.");
                                            pedirCarregador();
                                        }
                                        else {
                                            reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                                            reply.setContent("unk");                                            
                                        }        
                                        
                                        if(reply.getContent() != null)
                                            myAgent.send(reply);
				}
				else {
					block();
				}
			}
		} );
	}
        
        protected void pedirCarregador() {
            enviarMensagem("iniciar 300 7", "trabalho", "Carregador");
        }
        
        protected void informarDescarregador() {
            enviarMensagem("volume 300", "trabalho", "Descarregador");
        }
        
        protected void pedirRotaRoteador() {
            enviarMensagem("rota 3 5", "trabalho", "Roteador");
        }
        
        protected void enviarMensagem(String content, String ontology, String agent) {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent(content);
            msg.setProtocol("fipa-request");
            msg.setOntology(ontology);
            AID ag = new AID(agent, AID.ISLOCALNAME);
            msg.addReceiver(ag);
            System.out.println("==(Transportador) Mensagem -> " + msg.getContent() + " para " + ag.getLocalName());
            send(msg);
        }
}
