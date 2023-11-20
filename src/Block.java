import java.util.Date;
public class Block {

    private String data; //数据
    private long timeStamp; //1970年之后的时间戳
    public String hash; // 块的hash值
    public String previousHash; //前一个块的hash值
    private int nounce;
    // 构造函数
    public Block(String data,String previousHash){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    // 计算哈希值
    public String calculateHash(){
        String calculatedHash = StringUtil.applySha256(
                previousHash+
                        Long.toString(timeStamp)+
                        Integer.toString(nounce)+
                        data
        );
        return calculatedHash;
    }

    // 挖矿函数：看区块的hash值是否满足条件，满足条件，则挖矿成功
    public void mineBlock(int difficulty){
        // 定义条件，当前条件为前difficulty个位是0
        String target = new String(new char[difficulty]).replace('\0','0');
        // 循环挖矿，即看区块的hash值前difficulty位何时能与target相等
        while(! hash.substring(0,difficulty).equals(target)){
            // 多次尝试基于实时的hash值
            nounce ++; // nounce是矿工号，是工作量证明
            hash = calculateHash();
        }
        System.out.println("挖矿成功："+hash);
    }

}
