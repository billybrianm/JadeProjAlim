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
    
        public boolean onJob = false;
        
	protected void setup() {          
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					System.out.println("==(Transportador) Mensagem <- "+msg.getContent()+" de " +msg.getSender().getName());
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
                                            if(!onJob) {
                                                System.out.println("=>(Transportador) Agente Transportador iniciado.");
                                                reply.setPerformative(ACLMessage.INFORM);
                                                reply.setContent("true");
                                                requestCarregador();
                                            }
                                        }
                                        else if("true".equals(msg.getContent())) {
                                            System.out.println("==(Transportador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
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
        
        protected void requestCarregador() {
            sendMessage("iniciar 300 4", "trabalho", "Carregador");
        }
        
        protected void sendMessage(String content, String ontology, String agent) {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent(content);
            msg.setProtocol("fipa-request");
            msg.setOntology(ontology);
            AID ag = new AID(agent, AID.ISLOCALNAME);
            msg.addReceiver(ag);
            send(msg);
            System.out.println("==(Transportador) Mensagem -> " + msg.getContent() + " para " + ag.getLocalName());
            onJob = true;
    }
}
