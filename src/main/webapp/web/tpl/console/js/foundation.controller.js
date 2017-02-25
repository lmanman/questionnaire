
angular.module('app.console.foundation.controllers', [])
    .controller('FoundationCtrl', ['$scope','$stateParams','$log','$http',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$log,$http){
    $scope.id = 0;
    $scope.title = "";
    $scope.init = function(){
        $scope.id = $stateParams.id;
        $log.info("id="+$scope.id);
        if($scope.id == 0 || $scope.id == null){
            $log.info('xx');

        }else{
            $scope.title = $scope.brandTree[$scope.id-1].name;
        }
    };


    $scope.manufacturerList = [
        {"id":"1","name":"床垫厂商A","address":"上海市普陀区真北路1108号","telphone":"51099515","linkman":"张三"},
        {"id":"2","name":"家具厂商B","address":"上海市宝山区汶水路1555号","telphone":"61496666","linkman":"李四"},
        {"id":"3","name":"床垫厂商A","address":"上海市普陀区澳门路168号","telphone":"62665266","linkman":"王五"}
    ];


    $scope.brandTree = [
        {"id":"1","name":"圣象","mainId":2,"mainName":"建材","item":[{"id":"11","name":"圣象地板","item":[]}]},
        {"id":"2","name":"慕斯","mainId":3,"mainName":"家居饰品","item":[{"id":"21","name":"慕斯床垫","item":[{"id":"61","name":"慕斯.歌蒂娅"},{"id":"62","name":"慕斯.凯奇"},{"id":"63","name":"慕斯.3D"},{"id":"64","name":"慕斯.苏菲娜"}]}]},
        {"id":"3","name":"顾家","mainId":1,"mainName":"家具","item":[{"id":"31","name":"顾家沙发","item":[]}]},
        {"id":"4","name":"科勒","mainId":2,"mainName":"建材","item":[{"id":"51","name":"科勒卫浴","item":[]},{"id":"52","name":"科勒厨房","item":[]}]},
        {"id":"5","name":"海尔","mainId":4,"mainName":"电器","item":[{"id":"41","name":"卡萨帝","item":[]}]},
    ];

    $scope.brandAdd = function(temp){
        $scope.brandTree.push({"id":"0","name":temp,"mainId":2,"mainName":"建材","item":[]});
        $scope.title=temp;
    };
    $scope.deleteBrand = function(did){
        for (var i = 0; i < $scope.brandTree.length; i++) {
            if ($scope.brandTree[i].id == did) {
                $scope.brandTree.splice(i,1);
            } else {
                var item1 = $scope.brandTree[i].item;
                $log.info($scope.brandTree[i].id+"---"+item1.length);
                for (var j = 0; j < item1.length; j++) {
                    $log.info(item1[j].id);
                    if (item1[j].id == did) {
                        item1.splice(j, 1);
                    }else{
                        var item2 = item1[j].item;
                        for (var k = 0; k < item2.length; k++) {
                            if (item2[k].id == did) {
                                item2.item.splice(k, 1);
                            }
                        }
                    }
                }
            }
        }
    };


    $scope.categoryList = [
        {"type": "main", "typeName": "大类","level": 1,"item":[
            {"id": 1,  "name": "家具" },
            {"id": 2,  "name": "建材"},
            {"id": 3,  "name": "家居饰品"},
            {"id": 4,  "name": "电器"}]},
        {"type": "import", "typeName": "是否进口","level": 2,"item":[
            {"id": 70,  "name": "纯进口"},
            {"id": 71,  "name": "非进口"},
            {"id": 72,  "name": "含进口和国产"}]},
        {"type": "style", "typeName": "风格","level": 2,"item":[
            {"id": 57,  "name": "中式"},
            {"id": 58,  "name": "新中式"},
            {"id": 59,  "name": "简欧风格"},
            {"id": 60,  "name": "欧式风格"},
            {"id": 61,  "name": "功能性家具"},
            {"id": 62,  "name": "东南亚风格"},
            {"id": 63,  "name": "美式风格"},
            {"id": 64,  "name": "美式风格"},
            {"id": 65,  "name": "地中海风格"},
            {"id": 66,  "name": "现代简约"},
            {"id": 67,  "name": "日式"},
            {"id": 68,  "name": "韩式"},
            {"id": 69,  "name": "其他"}]},
        {"type": "function", "typeName": "功能","level": 2,"item":[
            {"id": 11,  "name": "客厅家具"},
            {"id": 12,  "name": "寝具"},
            {"id": 13,  "name": "儿童家具"},
            {"id": 14,  "name": "综合家具"},
            {"id": 15,  "name": "办公家具"},
            {"id": 16,  "name": "地板"},
            {"id": 17,  "name": "室外家具"},
            {"id": 18,  "name": "定制家居"},
            {"id": 19,  "name": "定制移门衣柜"},
            {"id": 20,  "name": "壁纸、壁布"},
            {"id": 21,  "name": "橱柜"},
            {"id": 22,  "name": "硅藻泥"},
            {"id": 23,  "name": "灯具"},
            {"id": 24,  "name": "饰品"},
            {"id": 25,  "name": "瓷砖"},
            {"id": 26,  "name": "石材"},
            {"id": 27,  "name": "涂料"},
            {"id": 28,  "name": "榻榻米"},
            {"id": 29,  "name": "地暖"},
            {"id": 30,  "name": "净水"},
            {"id": 31,  "name": "厨房电器"},
            {"id": 32,  "name": "热水器"},
            {"id": 33,  "name": "音响设备"},
            {"id": 34,  "name": "地毯"},
            {"id": 35,  "name": "其他"}]},
        {"type": "material", "typeName": "材质","level": 2,"item":[
            {"id": 36,  "name": "皮布结合"},
            {"id": 37,  "name": "皮质"},
            {"id": 38,  "name": "布艺"},
            {"id": 39,  "name": "藤竹"},
            {"id": 40,  "name": "红木"},
            {"id": 41,  "name": "实木"},
            {"id": 42,  "name": "板式"},
            {"id": 43,  "name": "板木"},
            {"id": 44,  "name": "石材"},
            {"id": 45,  "name": "玻璃"},
            {"id": 46,  "name": "塑料"},
            {"id": 47,  "name": "棕床垫"},
            {"id": 48,  "name": "弹簧床垫"},
            {"id": 49,  "name": "乳胶床垫"},
            {"id": 50,  "name": "3D面料床垫"},
            {"id": 51,  "name": "电动"},
            {"id": 52,  "name": "塑胶"},
            {"id": 53,  "name": "实木复合"},
            {"id": 54,  "name": "强化复合"},
            {"id": 55,  "name": "金属家具"},
            {"id": 56,  "name": "其他"}]}
    ];

    $scope.maxId = 60;

    $scope.categoryAdd = function(type){
        for (var i = 0; i < $scope.categoryList.length; i++) {
            var category = $scope.categoryList[i];
            if (category.type == type) {
                var flag=false;
                for(var j=0;j<category.item.length;j++){
                    if(category.item[j].id==0){
                        flag=true;
                        break;
                    }
                }
                if(!flag) {
                    category.item.push(
                        {
                            id: 0,
                            name: ""
                        }
                    );
                }
                break;
            }
        }
    };

    $scope.categorySave = function(type,temp){
        for (var i = 0; i < $scope.categoryList.length; i++) {
            var category = $scope.categoryList[i];
            if (category.type == type) {
                var item = category.item;
                item[category.item.length-1] = {
                    id: $scope.maxId++,
                    name: temp
                }
                break;
            }
        }
    };
    $scope.categoryDel = function(type,cid){
        for (var i = 0; i < $scope.categoryList.length; i++) {
            var category = $scope.categoryList[i];
            if (category.type == type) {
                for(var j=0;j<category.item.length;j++){
                    if(category.item[j].id==cid){
                        category.item.splice(j,1);
                        break;
                    }
                }
                break;
            }
        }
    };

    $scope.deleteManuf = function(index){
        $scope.manufacturerList.splice(index,1);
    }

    function submit(){

    };

}
