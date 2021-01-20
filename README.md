# 作成課題について

## 作成課題
https://docs.google.com/document/d/1Y8th5pfoD5iFl0EbNs7i_abqDQQSZgRg_F8mo-CJrGY/edit#heading=h.drx5gumg7pzs

## コンテナ環境

```
├──Docker
│   ├── Dockerfile
│   ├── cassandra.yaml
│   └── database.properties
├── build.sh
├── docker-compose.yml
├── stop.sh
└── svr
    ├── build
    ├── build.gradle
    ├── build.sh
    ├── cassandora.sh
    ├── exec.sh
    ├── loader.sh
    ├── generated.cql
    ├── libs
    │   └── scalardb-1.1.0.jar
    ├── schema
    │   └── demo-user.sdbql
    └── src
        ├── main
        │   ├── java
        │   │   └── com/example/demo
        │   │               ├── DemoApplication.java
        │   │               ├── MainController.java
        │   │               ├── UserModel.java
        │   │               └── UserService.java
        │   └── resources
        └── test
           └── java
                  └── com/example/demo
                           ├── DemoApplicationTests.java
                           └── MainControllerTest.java
```
コンテナの構成は、SpringBoot、ScalarDB、Cassandora全て１サーバで動作させています。

### ホスト側スクリプト

|Name|内容|
|:--|:--|
|build.sh|Dockerイメージビルド|
|start.sh|コンテナ起動|
|stop.sh|コンテナ終了|
|ssh.sh|コンテナへSSH接続|

### コンテナ側スクリプト

|Name|内容|
|:--|:--|
|cassandora.sh| cassandoraサービス起動|
|loader.sh|スキーマ作成|
|build.sh|APIビルド＆ユニットテスト|
|exec.sh|API起動|

### コンテナ起動手順
ホスト側で下記の作業を行います。

```
% build.sh    // Dockerイメージをビルド
% start.sh    // コンテナを起動
% ssh.sh      // コンテナへSSH接続
```

コンテナ側で下記の作業を行います。

```
% cassandora.sh    // cassandoraサービスを起動
% loader.sh            // スキーマ作成
% build.sh              // APIのビルドおよびユニットテスト
% exec.sh              // API起動
```

以上で、APIが起動されます。
URLは
　http://localhost:8080/user
となります。
コンテナの終了はホスト側で

```
% stop.sh
```

を実行します。


## スキーマ
今回は下記のスキーマを作成しました

```
REPLICATION FACTOR 1;
CREATE NAMESPACE demo;
CREATE TRANSACTION TABLE demo.user (
  group_id TEXT PARTITIONKEY,
  username TEXT CLUSTERINGKEY,
  id TEXT,
  firstname TEXT,
  lastname TEXT,
  email TEXT,
  password TEXT,
  phone TEXT,
  userstatus INT,
);
```

## Endpoint
|Endpoint|Method|内容|
|:--|:--|:--|
|/user|POST|ユーザ作成|
|/user/{username}|GET|ユーザ取得|
|/user/{username}|PUT|ユーザ更新|
|/user/{username}|DELETE|ユーザ削除|

## APIソース

|name|内容|
|:--|:--|
| DemoApplication.java|アプリケーションMain|
| MainController.java|コントローラー|
| UserModel.java|ユーザモデル|
| UserService.java|ユーザサービス|

## UnitTestソース

|name|内容|
|:--|:--|
| DemoApplicationTests.java|SpringBoot標準テストコード|
| MainControllerTest.java|コントローラーテスト|

