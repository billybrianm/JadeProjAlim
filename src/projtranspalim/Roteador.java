package projtranspalim;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * @v2.0.0
 * @author billy
 */
public class Roteador extends Agent{

    protected void setup() {          
            addBehaviour(new CyclicBehaviour(this) {
                    public void action() {
                            ACLMessage msg = myAgent.receive();
                            if (msg != null) {
                                    System.out.println("==(Roteador) Mensagem <- "+msg.getContent()+" de " +msg.getSender().getName());
                                    ACLMessage reply = msg.createReply();
                                    if ("vivo".equals(msg.getContent())) {
                                            reply.setPerformative(ACLMessage.INFORM);
                                            reply.setContent("vivo");
                                    }
                                    else if(msg.getContent().startsWith("rota")) {
                                        System.out.println("=>(Roteador) Agente Roteador iniciado.");
                                        reply.setPerformative(ACLMessage.INFORM);
                                        reply.setContent("hold");                                                                                        
                                        checarRotasControlador(msg.getContent().split(" ")[1], msg.getContent().split(" ")[2]);
                                    }
                                    else if(msg.getContent().startsWith("r_resp")) {
                                        //reply.setPerformative(ACLMessage.INFORM);
                                        //reply.setContent("true");
                                        if(msg.getContent().split(" ")[1].equals("true")) {
                                            enviarRotaTransportador();
                                        }
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
        
    protected void checarRotasControlador(String pos1, String pos2) {
        enviarMensagem("r_problemas " + pos1 + " " + pos2, "verificacao", "Controlador");
    }               

    protected void enviarRotaTransportador() {
        java.util.Random rand = new java.util.Random();
        int j = rand.nextInt(9)+1;
        enviarMensagem("rota rota" + j, "inform", "Transportador");
    }

    protected void enviarMensagem(String content, String ontology, String agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(content);
        msg.setProtocol("fipa-request");
        msg.setOntology(ontology);
        AID ag = new AID(agent, AID.ISLOCALNAME);
        msg.addReceiver(ag);
        System.out.println("==(Roteador) Mensagem -> " + msg.getContent() + " para " + ag.getLocalName());
        send(msg);
    }
}
