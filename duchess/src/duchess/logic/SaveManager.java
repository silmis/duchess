/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duchess.logic;

/**
 *
 * @author thitkone
 */
public class SaveManager {
    String filePath;
    public SaveManager() { }
    public String getPath() { return this.filePath; }
    public void setPath(String filePath) {
        this.filePath = filePath;
    }
    public boolean loadGame(String fileName) { 
        return true; 
    }
    public boolean write() {
        return true;
    }
}
