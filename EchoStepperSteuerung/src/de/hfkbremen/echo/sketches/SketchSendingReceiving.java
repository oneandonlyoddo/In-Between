

package de.hfkbremen.echo.sketches;


import processing.core.PApplet;


public class SketchSendingReceiving
        extends PApplet {

    private Serial mSerial;

    public void setup() {
        Serial.listPorts();
        mSerial = Serial.open("/dev/tty.SLAB_USBtoUART");
        delay(10);
       
       
        mSerial.write("#*p2\r");
        
 
    }

    public void draw() {
    }

    public void keyPressed() {
        if (key == '1') {
            mSerial.write("#*s100\r");
        }
        if (key == '2') {
            mSerial.write("#*s200\r");
        }
        if (key == '3') {
            mSerial.write("#*s300\r");
        }
        if (key == '4') {
            mSerial.write("#*s400\r");
        }
        if (key == '5') {
            mSerial.write("#*A\r");
        }
        
    }

    public static void main(String[] args) {
        PApplet.main(new String[] {SketchSendingReceiving.class.getName()});
    }
}
