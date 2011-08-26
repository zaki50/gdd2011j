.class final Lcom/google/android/apps/gddquiz/gddquiz11service/a;
.super Lcom/google/android/apps/gddquiz/a;


# instance fields
.field private synthetic a:Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;


# direct methods
.method constructor <init>(Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;)V
    .locals 0

    iput-object p1, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/a;->a:Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;

    invoke-direct {p0}, Lcom/google/android/apps/gddquiz/a;-><init>()V

    return-void
.end method


# virtual methods
.method public final a()Ljava/lang/String;
    .locals 4

    iget-object v0, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/a;->a:Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;

    const-string v1, "gddquiz"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    const-string v1, "email"

    const-string v2, ""

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "passphrase"

    const-string v3, ""

    invoke-interface {v0, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11Service;->a(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
