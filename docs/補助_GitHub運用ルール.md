# 補助_GitHub運用ルール

> **役割**：このプロジェクトのGitHub運用の基準。`02_開発ルール` の補足。

---

## 1. 基本

- すべての資料・コード・成果物はGitHubで管理する
- リポジトリは原則 **public**（チャットAIが raw URL で前提資料を参照できるようにするため）
- 機密情報（APIキー・個人情報）はコミットしない

## 2. ブランチ

- `main`：安定版・リリース用
- 開発を分ける場合は `dev` で作業し、安定したら `main` へマージ
- 小規模なら `main` 直接でもよい（2台運用なら pull/push 徹底）

## 3. コミット

- 1つの意味のある単位ごとにコミットする
- メッセージは簡潔に（例：`feat: add ruby ore`, `fix: crash on mining`, `docs: update spec`）
- プレフィックス例：`feat`（機能）/`fix`（修正）/`docs`（文書）/`chore`（雑務）/`refactor`

## 4. .gitignore（生成物は除外）

```
.gradle/  build/  out/  bin/  run/  logs/  *.log
.idea/  *.iml  .vscode/  .DS_Store  Thumbs.db  node_modules/
```

## 5. Issue / PR

- 不具合や要望は Issue に記録（`.github/ISSUE_TEMPLATE` のテンプレ使用）
- 変更は PR テンプレに沿って説明する（個人開発でも履歴として有用）

## 6. 複数PC運用

- 作業前 `git pull` / 作業後 `git push` / 片方ずつ（詳細は `02_開発ルール` 第10章）
