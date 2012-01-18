

package de.hfkbremen.echo.sketches;


import processing.core.PApplet;
import controlP5.*;
import oscP5.*;
import netP5.*;

public class MotorInterface
        extends PApplet {

    private Serial mSerial;
   final int numberOfLeinwaende = 7;
   Leinwand[] leinwaende;
   ControlP5 controlP5;
   int margin = 10;
   int breiteSingleView=150;
   int hoeheSingleView=200;
   
   OscP5 oscObjekt;
   NetAddress oscEmpfaenger;
  
    public void setup() {
        size(1280,700);
        background(0);
        ellipseMode(CENTER);
        smooth();
        Serial.listPorts();
        //mSerial = Serial.open("/dev/tty.SLAB_USBtoUART");
        //mSerial.write("#*@A\r");
        //mSerial.write("#*p2\r");
        //mSerial.write("#*o60\r");
        controlP5 = new ControlP5(this);
        leinwaende = new Leinwand[ numberOfLeinwaende ];
        for ( int i = 0; i < numberOfLeinwaende; i++ ) {
          
                leinwaende[ i ] = new Leinwand(margin+i*(breiteSingleView+margin),margin,i);
                controlP5.addSlider("winkel"+i,0,360,0,margin+i*(breiteSingleView+margin),margin+hoeheSingleView+margin,110,10).setId(i);
            
        }
        //oscObjekt = new OscP5(this,3000);
        //oscEmpfaenger = new NetAddress("David.local",3000);
        controlP5.addButton("globalGo",0,1200,10,50,20).setId(numberOfLeinwaende+1);
        
        
    }

    public void draw() {
        background(0);
        for ( int i = 0; i < numberOfLeinwaende; i++ ) {
          leinwaende[ i ].display();  
        }
        
        
   
    }
    
    public void keyPressed(){
        if (key == 'r'|| key == 'R'){
            for ( int i = 0; i < numberOfLeinwaende; i++ ) {
                controlP5.controller("winkel"+i).setValue(0);
            }
        }
    }
    
    public class Leinwand{
        
        float winkel;
        float currentAngle;
      
        int id;
        int positionX;
        int positionY;
        int startTime;
        Leinwand(int X, int Y,int i) {
        
        winkel = 0;
        currentAngle =0;
        positionX = X;
        positionY = Y;
        
        id=i;
        
        
        
        
        }
        
        void display(){
            pushMatrix();
            translate(positionX, positionY);
            text("Motor "+id, 5, 15); 
            text("Angle: "+winkel+"°", 5, 30);
            strokeWeight(1);
            stroke(50);
            noFill();
            rect(0,0,150,200);
            strokeWeight(3);
            stroke(255);
            ellipse(75,125, 140,140);
            pushMatrix();
            translate(75, 125);
            pushMatrix();
            noStroke();
            fill(0,54,82);
            ellipse(0,0, 130,130);
            strokeWeight(2);
            stroke(0,105,140);
            actualAngle();
            rotate(radians(currentAngle));
            triangle(0,-50,-3,-47,3,-47);
            line(0,-50,0,50);
            popMatrix();
            rotate(radians(winkel));
            strokeWeight(3);
            stroke(255);
            triangle(0,-50,-3,-47,3,-47);
            line(0,-50,0,50);
            popMatrix();
            popMatrix();
        }
        
        float actualAngle(){
            float angle=currentAngle;
            while (millis()<startTime+winkel*(6000/360)){
                angle++;
                currentAngle=angle;
            }
             
             return angle;
            
            
        }
        
        float angle(int schritte) {
            float angle=0;
            if (schritte*0.9f<=360){
                angle=schritte*0.9f;
            }else if (schritte*0.9f>360){
                angle=(schritte*0.9f)%360;
            }
            return angle;
        }
        
        
        
        
       
    }
    
    void controlEvent(ControlEvent theEvent) {
            println("got a control event from controller with id "+theEvent.controller().id());
            if (theEvent.controller().id()<=numberOfLeinwaende){
                leinwaende[theEvent.controller().id()].winkel=theEvent.controller().value();
                //mSerial.write("#"+theEvent.controller().id()+"s"+theEvent.controller().value()+"\r");
            }else if (theEvent.controller().id()==numberOfLeinwaende+1){
                globalGO();
            }
           
                
                
                
            /*switch(theEvent.controller().id()) {
                case(0):
                    leinwaende[0].winkel=theEvent.controller().value();
                    break;
                case(1):
                    leinwaende[1].winkel=theEvent.controller().value();
                    break;
                case(2):
                    leinwaende[2].winkel=theEvent.controller().value();
                    break;
                case(3):
                    leinwaende[3].winkel=theEvent.controller().value();
                break;  
            }*/
        }
    
    public void globalGO(){
        //mSerial.write("#*A\r");
        //OscMessage myMessage = new OscMessage("Motorposition");
        //myMessage.add(180.8); /* add an int to the osc message */
        //oscObjekt.send(myMessage, oscEmpfaenger);
        for (int i=0; i<=numberOfLeinwaende;i++){ 
            if (leinwaende[i].currentAngle<leinwaende[i].winkel){
                leinwaende[i].startTime = millis();
                println("und Go!");
            }
        }
    }
    
    

    public static void main(String[] args) {
        PApplet.main(new String[] {MotorInterface.class.getName()});
    }
}
