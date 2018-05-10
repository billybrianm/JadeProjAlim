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
    
    private int qtde;
    private int idEstoque;
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
					if("vivo".equals(msg.getContent())) {
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                        }
                                        else if(msg.getContent().startsWith("iniciar")) {
                                            System.out.println(">=(Carregador) Agente Carregador iniciado.");
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("true");
                                            String[] s = msg.getContent().split(" ");
                                            qtde = Integer.parseInt(s[1]);
                                            idEstoque = Integer.parseInt(s[2]);
                                            System.out.println(">=(Carregador) " + qtde + " itens serão carregados ao estoque " + idEstoque);
                                        }
                                        else if("true".equals(msg.getContent())) {
                                            System.out.println("==(Carregador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
                                        }
                                        else {
                                            reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                                            reply.setContent("mens-desconhecida");                                            
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
