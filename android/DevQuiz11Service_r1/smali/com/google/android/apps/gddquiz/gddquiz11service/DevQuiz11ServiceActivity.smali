.class public Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;
.super Landroid/app/Activity;


# instance fields
.field private a:Landroid/widget/EditText;

.field private b:Landroid/widget/EditText;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method

.method private a()V
    .locals 4

    const-string v0, "gddquiz"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    iget-object v1, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->a:Landroid/widget/EditText;

    const-string v2, "email"

    const-string v3, ""

    invoke-interface {v0, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->b:Landroid/widget/EditText;

    const-string v2, "passphrase"

    const-string v3, ""

    invoke-interface {v0, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    return-void
.end method


# virtual methods
.method public onClickSave(Landroid/view/View;)V
    .locals 3

    const-string v0, "gddquiz"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "email"

    iget-object v2, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->a:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    const-string v1, "passphrase"

    iget-object v2, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->b:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1

    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    const/high16 v0, 0x7f03

    invoke-virtual {p0, v0}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->setContentView(I)V

    const/high16 v0, 0x7f06

    invoke-virtual {p0, v0}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->a:Landroid/widget/EditText;

    const v0, 0x7f060001

    invoke-virtual {p0, v0}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->b:Landroid/widget/EditText;

    invoke-direct {p0}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->a()V

    return-void
.end method

.method protected onRestoreInstanceState(Landroid/os/Bundle;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/app/Activity;->onRestoreInstanceState(Landroid/os/Bundle;)V

    invoke-direct {p0}, Lcom/google/android/apps/gddquiz/gddquiz11service/DevQuiz11ServiceActivity;->a()V

    return-void
.end method
