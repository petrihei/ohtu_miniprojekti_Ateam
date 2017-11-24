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
    ArrayList<String> inputList;
    int mones;
    ArrayList<String> outputs;

    public IOStub(String... inputs) {
        this.inputs = inputs;
        this.outputs = new ArrayList<>();
    }

    public IOStub(ArrayList<String> inputs){
        this.inputList = inputs;
        this.outputs = new ArrayList<>();
    }

    public String nextLine() {
        if (inputs != null){
            return inputs[mones++];
        }
        if (inputList != null){
            return inputList.get(mones++);
        }
        return null;
    }

    public void print(String m) {
        outputs.add(m);
    }

    public ArrayList<String> getOutputs(){
        return this.outputs;
    }
}
