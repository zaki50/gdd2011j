.class public Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;
.super Landroid/app/Service;


# static fields
.field private static final a:[C


# instance fields
.field private final b:Lcom/google/android/apps/gddquiz/a;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/16 v0, 0x10

    new-array v0, v0, [C

    fill-array-data v0, :array_0

    sput-object v0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->a:[C

    return-void

    :array_0
    .array-data 0x2
        0x32t 0x0t
        0x34t 0x0t
        0x33t 0x0t
        0x35t 0x0t
        0x37t 0x0t
        0x30t 0x0t
        0x42t 0x0t
        0x45t 0x0t
        0x44t 0x0t
        0x31t 0x0t
        0x43t 0x0t
        0x36t 0x0t
        0x39t 0x0t
        0x38t 0x0t
        0x46t 0x0t
        0x41t 0x0t
    .end array-data
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Landroid/app/Service;-><init>()V

    new-instance v0, Lcom/google/android/apps/gddquiz/gddquiz11service/a;

    invoke-direct {v0, p0}, Lcom/google/android/apps/gddquiz/gddquiz11service/a;-><init>(Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;)V

    iput-object v0, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->b:Lcom/google/android/apps/gddquiz/a;

    return-void
.end method

.method static synthetic a(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    invoke-static {p0, p1}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->b(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private static b(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 6

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_0

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    :cond_0
    const-string v0, "Please set the account and pass code to DevQuiz 2011 app."

    :goto_0
    return-object v0

    :cond_1
    :try_start_0
    const-string v2, "SHA-1"

    invoke-static {v2}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v3, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "8859_1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v2, 0x0

    :goto_1
    const/16 v3, 0xa

    if-lt v2, v3, :cond_2

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_2
    aget-byte v3, v0, v2

    add-int/lit8 v4, v2, 0xa

    aget-byte v4, v0, v4

    xor-int/2addr v3, v4

    sget-object v4, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->a:[C

    shr-int/lit8 v5, v3, 0x4

    and-int/lit8 v5, v5, 0xf

    aget-char v4, v4, v5

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    sget-object v4, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->a:[C

    and-int/lit8 v3, v3, 0xf

    aget-char v3, v4, v3

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    rem-int/lit8 v3, v2, 0x2

    const/4 v4, 0x1

    if-ne v3, v4, :cond_3

    const/16 v3, 0x20

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/io/UnsupportedEncodingException; {:try_start_0 .. :try_end_0} :catch_0

    :cond_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    :catch_0
    move-exception v0

    :goto_2
    const-string v0, ""

    goto :goto_0

    :catch_1
    move-exception v0

    goto :goto_2
.end method


# virtual methods
.method public onBind(Landroid/content/Intent;)Landroid/os/IBinder;
    .locals 1

    iget-object v0, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->b:Lcom/google/android/apps/gddquiz/a;

    return-object v0
.end method
