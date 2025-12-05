package hash_lab7;

public class Entry {
    public String username;
    private long pos;
    public Entry next;
    
    public Entry (String username, long pos, Entry next){
        this.username = username;
        this.pos = pos;
        this.next=null;
    }
    
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    
    public Long getPos(){
        return pos;
    }
    public void setPos(Long pos){
        this.pos=pos;
    }
    
    public Entry getNext(){
        return next;
    }
    public void setNext (Entry next){
        this.next=next;
    }
}
