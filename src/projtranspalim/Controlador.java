package projtranspalim;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


/**
 *
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
                    System.out.println("==(Controlador) Mensagem <- " + msg.getContent()
                    + " de " + msg.getSender().getName());
                
                    if("true".equals(msg.getContent())) {
                        System.out.println("==(Controlador) recebeu uma resposta true de " + msg.getSender().getName() + " e agora está em stand-by.");
                    }
                }
                block();
            }
        });
        sendMessage("vivo", "presenca", "Transportador");
        sendMessage("iniciar", "trabalho", "Transportador");
    }
    
    protected void sendMessage(String content, String ontology, String agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(content);
        msg.setProtocol("fipa-request");
        msg.setOntology(ontology);
        AID ag = new AID(agent, AID.ISLOCALNAME);
        msg.addReceiver(ag);
        send(msg);
        System.out.println("==(Controlador) Mensagem -> " + msg.getContent() + " para " + ag.getLocalName());
    }
}
