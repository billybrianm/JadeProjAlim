package projtranspalim;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
/**
 * @v1.0.0
 * @author billy
 */
public class Descarregador extends Agent{
	protected void setup() {          
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					System.out.println("==(Descarregador) Mensagem <- "+msg.getContent()+" de " +msg.getSender().getName());
					ACLMessage reply = msg.createReply();

					if("vivo".equals(msg.getContent())) {
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                        }
                                        else if(msg.getContent().startsWith("volume")) {
                                            System.out.println(">=(Carregador) Agente Descarregador iniciado.");
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("release");
                                            System.out.println("=>(Descarregador) " + msg.getContent().split(" ")[1] + " itens estão presentes no caminhão.");
                                            System.out.println("=>(Descarregador) Descarregando caminhão...");
                                            System.out.println("=>(Descarregador) Caminhão descarregado com sucesso. Transportador liberado.");
                                        }
                                        else if("true".equals(msg.getContent())) {
                                            System.out.println("==(Descarregador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
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
}
