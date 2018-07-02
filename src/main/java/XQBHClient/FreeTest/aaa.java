package XQBHClient.FreeTest;

public class aaa {
    public static void main(String[] args) {
        byte b[] = {0x00,   0x00, 0x00, 0x00, 0x00,              0x00, 0x00, 0x00, 0x00, 0x00};
        byte c[]={0x01,0x02,0x04,0x08,0x10,0x20,0x40, (byte) 0x80};
        int x=4;
        int y=6;
        if (x>=32||y>=32)
        {

        }
        int p=x/4;
        int q=x%4;
        b[4-p]=c[q];
        p=y/4;
        q=y%4;
        b[8-p]=c[q];
        for (byte sub:
             b) {
            System.out.print(sub + " ");

        }
        System.out.println();
    }
}
