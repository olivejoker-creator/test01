# test01

Minecraft MOD プロジェクト

## 複数PCでの運用ルール（2台交互）

このリポジトリは複数のPCから同じmodを編集する。事故（コンフリクト）を防ぐため、必ず守る。

### 黄金ルール
- 作業を始める前：git pull（相手PCの最新を取り込む）
- 作業を終えたら：git push（自分の変更を上げる）
- 2台で同時に触らない（必ず片方ずつ）

### 流れ
1. PCで作業を始める前に pull
2. 作業する（Claude Code / 取り込み / 直接編集）
3. 席を立つ前に必ず push
4. もう一方のPCでは、始める前に pull してから作業

### 注意
- push を忘れて別PCに移らない（座ったら pull、立つ前に push）
- どちらが最新か迷ったら GitHub（クラウド）が正。作業前に pull すれば揃う
- push が衝突（rejected / non-fast-forward）したら、Claude Code に「git pull してコンフリクトを解消し、変更を消さないよう安全にマージして push し直して」と頼む
- ファイル編集はできるだけ Claude Code に寄せる（pull→作業→push を自動でやるため事故が減る）
