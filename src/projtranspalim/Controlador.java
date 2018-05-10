package projtranspalim;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


/**
 * @v1.0.0
 * @author billy
 */
public class Controlador extends Agent {
    protected void setup() {        
        System.out.println("Agente controlador iniciado.");
        System.out.println("Para o início do ciclo de transporte, deve-se informar o Agente Transportador a quantidade de objetos e o estoque destino.");
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();               
                if(msg != null) {
                    ACLMessage reply = msg.createReply();
                    
                    System.out.println("==(Controlador) Mensagem <- " + msg.getContent()
                    + " de " + msg.getSender().getName());
                
                    if("true".equals(msg.getContent())) {
                        System.out.println("==(Controlador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
                    }
                    else if(msg.getContent().startsWith("r_problemas")) {
                        analisarRota(msg.getContent().split(" ")[1], msg.getContent().split(" ")[2]);
                        System.out.println("==(Controlador) recebeu um request de  " + msg.getSender().getName() + " e está o analisando.");
                    }
                    else {
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("unk");
                    }
                }
                block();
            }
        });
        startTransportador();
    }
    
    protected void startTransportador() {
        sendMessage("iniciar", "trabalho", "Transportador");
    }
    
    protected void analisarRota(String pos1, String pos2) {
        // CONTROLADOR ANALISA SE EXISTEM PROBLEMAS NA ROTA
        sendMessage("r_resp true", "inform", "Roteador");
    }
    
    protected void sendMessage(String content, String ontology, String agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(content);
        msg.setProtocol("fipa-request");
        msg.setOntology(ontology);
        AID ag = new AID(agent, AID.ISLOCALNAME);
        msg.addReceiver(ag);
        System.out.println("==(Controlador) Mensagem -> " + msg.getContent() + " para " + ag.getLocalName());
        send(msg);
    }
}
