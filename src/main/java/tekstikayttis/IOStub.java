/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekstikayttis;

import java.util.ArrayList;

/**
 *
 * @author petriheinonen
 */
public class IOStub implements IO{
    
    String[] inputs;
    int mones;
    ArrayList<String> outputs;

    public IOStub(String... inputs) {
        this.inputs = inputs;
        this.outputs = new ArrayList<String>();
    }

    public String nextLine() {
        return inputs[mones++];
    }

    public void print(String m) {
        outputs.add(m);
    }
    
}
