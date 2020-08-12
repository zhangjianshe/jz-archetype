### 项目目录

    创建脚手架  mvn archetype:create-from-project
    安装脚手架到本地 cd target\generated-sources\archetype
              mvn install
    mvn archetype:crawl 执行以上命令后在本地仓库的根目录中会生成archetype-catalog.xml文件
    创建新项目
    mvn archetype:generate -DarchetypeGroupId=com.ziroom -DarchetypeArtifactId=jz-archetype-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=com.ziroom -DartifactId=hddp-order -Dpackage=com.ziroom.hddp.order -Dversion=1.0.0 -DappName=order
    