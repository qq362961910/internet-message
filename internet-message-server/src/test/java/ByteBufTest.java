import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        testDuplicated();
    }
    public static void testDuplicated() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(0);
        buf.writeInt(1000);
        System.out.println(buf.writerIndex());
        ByteBuf buf2 = buf.duplicate().writeInt(10000);
        System.out.println(buf.writerIndex());
    }

    public static void testWriteIndex() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(0);
        buf.writeInt(1000);
        int indexBegin = buf.writerIndex();
        buf.writeZero(4);
        buf.writeInt(8888);
        buf.writeInt(9999);
        buf.markWriterIndex();
        int indexEnd = buf.writerIndex();
        buf.writerIndex(indexBegin);
        buf.writeInt(indexEnd - indexBegin);
        buf.resetWriterIndex();
        int i = buf.readInt();
        System.out.println("int: " + i);
        i = buf.readInt();
        System.out.println("int: " + i);
        i = buf.readInt();
        System.out.println("int: " + i);
        i = buf.readInt();
        System.out.println("int: " + i);
    }
}
