package org.zakky.gdd2011.android;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CodeGenerator {
    private static final String HASH_ALG = "SHA-1";
    
    private static final char[] a = "243570BED1C698FA".toCharArray();
    
    public String getCode(String googleAccount, String passcode) {
        /*
    .locals 6

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0
        */
        googleAccount = googleAccount.trim();
        /*
    invoke-virtual {v0}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v0
        */
        googleAccount = googleAccount.toLowerCase();
        //親切心で追加。メールアドレスの形式でもOK!
        if (googleAccount.indexOf('@') != -1) {
            googleAccount = googleAccount.substring(0, googleAccount.indexOf('@'));
        }

        /*
    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1
        */
        passcode = passcode.trim();

        /*
    invoke-virtual {v1}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v1
        */
        passcode = passcode.toLowerCase();

        /*
    :try_start_0
    const-string v2, "SHA-1"

    invoke-static {v2}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v2
         */
        try {
            final MessageDigest digester = MessageDigest.getInstance(HASH_ALG);

        /*
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0
         */
            googleAccount = String.valueOf(googleAccount);

        /*
    invoke-direct {v3, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V
         */
            final StringBuilder sb = new StringBuilder(googleAccount);

        /*
    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0
         */
            sb.append(passcode);

        /*
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0
         */
            final String source = sb.toString();

        /*
    const-string v1, "8859_1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v0
         */
            final byte[] sourceBytes = source.getBytes("8859_1");

        /*
    invoke-virtual {v2, v0}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v0
         */
            final byte[] digest = digester.digest(sourceBytes);

        /*
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V
         */
            final StringBuilder sb2 = new StringBuilder();

        /*
    const/4 v2, 0x0

    :goto_0
    const/16 v3, 0xa

    if-lt v2, v3, :cond_0

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0
         */
            int v2 = 0;
            while (true ) {
            int v3 = 10;
            if (v2 >= v3) {

        /*
    :goto_1
    return-object v0
         */
                return sb2.toString();
            }
        /*
    :cond_0
    aget-byte v3, v0, v2
         */
            byte b = digest[v2];

        /*
    add-int/lit8 v4, v2, 0xa
         */
            byte v4 = (byte) (v2 + 10);

        /*
    aget-byte v4, v0, v4
         */
            v4 = digest[v4];

        /*
    xor-int/2addr v3, v4
         */
            b ^= v4;

        /*
    sget-object v4, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->a:[C
         */

        /*

    shr-int/lit8 v5, v3, 0x4
         */
            int i = (b >> 4);
        /*

    and-int/lit8 v5, v5, 0xf
         */
            i &= 0xf;
        /*

    aget-char v4, v4, v5
         */
            char v = a[i];
        /*

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;
        */
            sb2.append(v);

         /*
    sget-object v4, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->a:[C

    and-int/lit8 v3, v3, 0xf
        */
            int k = b & 0xf;

         /*
    aget-char v3, v4, v3
        */
            v = a[k];

         /*
    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;
        */
            sb2.append(v);

         /*
    rem-int/lit8 v3, v2, 0x2
        */
            int vv3 = v2 % 2;

         /*
    const/4 v4, 0x1

    if-ne v3, v4, :cond_1
        */
            if (vv3 == 1) {

         /*
    const/16 v3, 0x20
        */
            

         /*
    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;
        */
                sb2.append((char) 0x20);
            }
                v2 += 1;
            }
         /*
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/io/UnsupportedEncodingException; {:try_start_0 .. :try_end_0} :catch_0
         */
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
            /*
    :cond_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :catch_0
    move-exception v0

    :goto_2
    const-string v0, ""

    goto :goto_1

    :catch_1
    move-exception v0

    goto :goto_2
         */
    }
    public static void main(String[] args) {
        final String code = new CodeGenerator().getCode("makoto1975", "0bz3jeq5dj");
        System.out.println(code);
    }
}
