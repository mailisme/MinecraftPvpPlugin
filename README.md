 # Minecraft PVP Plugin

## 規則
**道具:**

(註：道具皆為一次性)
| 道具           | 描述                                           |持續時間(作用時間)    |
|-----------------|----------------------------------------------|---------------------|
| 虛影斗篷         | 使玩家在短時間內隱形，進行偷襲或逃跑                               |5s|
| 劍魂之石         | 短時間內提升武器傷害                                             |10s|
| 風行之靴         | 提升玩家短時間內的移動速度                                       |10s|
| 時空之錨         | 投擲後過了一段固定的時間，就會自動把玩家傳送回丟置點                |15s|
| 冷陸氣團         | 投擲後在區域內造成冰凍效果，固定敵人在原地                          |5s|
| 陰毒之癌         | 投擲後在區域內散發輻射線，碰到時會對敵人(以及可能自己)造成短暫傷害   |10s|
| 地球之心         | 創造一個地震，對敵人造成向外的衝擊                               |瞬間|
| 反射之盾         | 單次反彈部分受到的傷害。                                        |瞬間|
| 煉獄之火         | 放置陷阱，使踩中的敵人受到燃燒傷害。                                |∞|

**技能:**

(註：每個人在進入遊戲前都可以選擇一個技能，技能就是一個在遊戲一開始就有的道具)  
(註：技能是可以重複使用的，不過有冷卻時間)

| 技能           | 描述                                                                                       |
|----------------|-------------------------------------------------------------------------------------------|
| 閃電突襲          | 瞬移到敵人旁邊，自動釋放身上隨機一個道具                                                   |
| 同歸於盡          | 血量越低，攻擊力越高，防禦力越低                                                           |
| 瞬間治癒          | 立即回復隨機量的生命值。如果回復量超過可以回復的量，則從原本的量倒扣超過的量 (EX: 15 + 7 -> 13)|
| 回收再利用        | 讓道具有50%的機率用完後繼續存在，5%機率讓敵人撿到                                            |
| 苦力怕             |按住Shift可以隱形靠近敵人，放開會爆炸                                                         |


## 如何貢獻PVP地圖

1. 裝 Java 21、Java JDK 21
2. `git clone https://github.com/mailisme/MinecraftPvpPlugin.git -b world` (如果之前就有 clone git 的 repo 最好先備份起來)
3. 進入 `MinecraftPvpPlugin/MinecraftServer` -> 執行 `start.bat`
4. 打開 Minecraft 1.8.x 版本，多人 -> 連上 `localhost` 伺服器
5. 進入大廳後，握著鑽石劍點右鍵，會有一個選單出現，請點上面唯一一個選項(應該是斧頭)，以傳送到PVP世界
6. 更改地圖
7. 當你告一個段落，將`MinecraftServer/PVP1/region`裡的所有檔案利用 Github UI 上傳至 `world` branch 的 `MinecraftServer/PVP1/region`，並取一個適當的commit名稱
8. 大功告成！
     
註：若有人更動了地圖並上傳至Github了，請在自己更動前先`git pull`同步地圖

## Bug(遊玩特色)
1. ~~玩家飢餓值reset~~
2. ~~player /l error~~
3. multi pvp place bug(未確定)
4. PVP還是會生怪
5. remove item
6. 目前用劍在進PVP的時候如果有方塊在可及範圍內就會偵測不到(not very important)
7. ~~鑽石劍耐用度恢復~~
