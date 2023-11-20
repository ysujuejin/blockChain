/**
 * 区块链类
 */
import java.util.ArrayList;
import com.google.gson.GsonBuilder;
public class BlockChain {
    public static ArrayList<Block> blockChain = new ArrayList<Block>();
    public static int difficulty = 5; // 设置为5的挖矿时间比较长

    /**下面的方法判断区块链是否有效，
     * 即当前块的hash值是否等于计算出来的hash，当前块的previousHash是否等于上个块的hash
     * 是否挖到矿，即是否当前块的hash值满足条件
     * 一旦有一个条件不满足，该区块链宣布无效*/
    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0','0');

        // 遍历区块链
        for(int i=1;i<blockChain.size();i++){
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);

            if(! currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("当前块的hash值不等于计算出来的hash值");
                return false;
            }

            if (! currentBlock.previousHash.equals(previousBlock.hash)){
                System.out.println("当前块的previousHash值不等于上个块的hash值");
                return false;
            }

            if(! currentBlock.hash.substring(0,difficulty).equals(hashTarget)){
                System.out.println("当前块还没有被挖掘");
                return false;
            }
        }

        return true;

    }
    public static void main(String[] args) {
        Block genesisBlock = new Block("first block","0");
        System.out.println("hash for block1:"+genesisBlock.hash);
        blockChain.add(genesisBlock);
        System.out.println("Try to mine block1...");
        blockChain.get(0).mineBlock(difficulty);

        Block secondBlock = new Block("second block",genesisBlock.hash);
        System.out.println("hash for block2:"+secondBlock.hash);
        blockChain.add(secondBlock);
        System.out.println("Try to mine block2...");
        blockChain.get(1).mineBlock(difficulty);

        Block thirdBlock = new Block("third block",secondBlock.hash);
        System.out.println("hash for block3:"+thirdBlock.hash);
        blockChain.add(thirdBlock);
        System.out.println("Try to mine block3...");
        blockChain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is valid:"+isChainValid());
        // 其实感觉这句代码显得有点鸡肋了，只有挖成功才可以进行这条语句

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("\nThe block chain:");
        System.out.println(blockchainJson);
    }
}
