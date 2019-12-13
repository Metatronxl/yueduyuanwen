class Solution {
    class Node {
        int val;
        int choice;
        // 0 代表啥都不做
        // 1 代表插入
        // 2 代表删除
        // 3 代表替换
        
        Node() {}
        Node(int val, int choice) { 
            this.val = val;
            this.choice = choice;
        }
    }
    
    public int minDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        Node[][] dp = new Node[m + 1][n + 1];
        // base case 
        for (int i = 0; i <= m; i++)
            dp[i][0] = new Node(i, 2);
        for (int j = 1; j <= n; j++)
            dp[0][j] = new Node(j, 1);
        
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    Node node = dp[i - 1][j - 1];
                    dp[i][j] = new Node(node.val, 0);
                } else {              
                    dp[i][j] = min(
                        dp[i - 1][j],
                        dp[i][j - 1],
                        dp[i-1][j-1]
                    );
                    dp[i][j].val++;
                }
        print(dp, s1, s2);
        // debug(dp);
        return dp[m][n].val;
    }
    
    private void print(Node[][] dp, String s1, String s2) {
        int rows = dp.length;
        int cols = dp[0].length;
        int i = rows - 1, j = cols - 1;
        System.out.println("Changing s1=" + s1 + " to s2=" + s2 + ":\n");
        while (i != 0 && j != 0) {
            char c1 = s1.charAt(i - 1);
            char c2 = s2.charAt(j - 1);
            int choice = dp[i][j].choice;
            System.out.print("s1[" + (i - 1) + "]:");
            switch (choice) {
                case 0:
                    i--; j--;
                    System.out.println("skip " + c1);
                    break;
                case 1:
                    System.out.println("insert " + c2);
                    j--;
                    break;
                case 2:
                    i--;
                    System.out.println("delete " + c1);
                    break;
                case 3:
                    i--; j--;
                    System.out.println("replace " + c1 + " as " + c2);
                    break;
            }
        }
        
        while (i > 0) {
            System.out.print("s1[" + (i - 1) + "]:");
            System.out.println("delete " + s1.charAt(i - 1));
            i--;
        }            
        while (j > 0) {
            System.out.print("s1[0]:");
            System.out.println("insert " + s2.charAt(j - 1));
            j--;
        }
    }
    
    private void debug(Node[][] dp) {
        for (Node[] row : dp) {
            for (Node n : row) {
                System.out.print(n.val + "  ");
            }
            System.out.println();
        }        
        System.out.println();
        System.out.println();
        
        for (Node[] row : dp) {
            for (Node n : row) {
                System.out.print(n.choice + "  ");
            }
            System.out.println();
        }
    }
    
    // delete, insert, replace
    private Node min(Node a, Node b, Node c) {
        Node res = new Node();
        res.val = a.val;
        res.choice = 2;
        
        if (res.val > b.val) {
            res.val = b.val;
            res.choice = 1;
        }
        if (res.val > c.val) {
            res.val = c.val;
            res.choice = 3;
        }
        return res;
    }
}
