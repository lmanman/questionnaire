
angular.module('app.console.surveyType.controllers', [])
    .controller('SurveyTypeCtrl', ['$scope','$log','$http',ctrlFn]);
;


function ctrlFn($scope,$log,$http){

    $scope.publicFieldList = [
        {"fieldName":"publicExhibitionExist","fieldDesc":"公区摆是否有","fieldFormat":"radio","relationData":"exist","indicator":"publicResource","formId":1,"orderId":80,
            "optionList":[{"id":252,"code":"N","name":"无"},{"id":253,"code":"Y","name":"有"}]
        },
        {"fieldName":"publicExhibitionArea","fieldDesc":"公区摆展面积","fieldFormat":"radio","relationData":null,"indicator":"publicResource","formId":1,"orderId":81,
            "optionList":[{"id":26,"code":"A","name":"5㎡以下"},
                {"id":27,"code":"B","name":"5-10㎡"},
                {"id":28,"code":"C","name":"10-20㎡"},
                {"id":29,"code":"D","name":"20㎡以上"},
            ]
        },
        {"fieldName":"publicExhibitionFloor","fieldDesc":"公区摆展楼层","fieldFormat":"number","relationData":"exist","indicator":"publicResource","formId":1,"orderId":82},
        {"fieldName":"publicExhibitionPriceTag","fieldDesc":"公区摆展是否有有价签","fieldFormat":"radio","relationData":"exist","indicator":"publicResource","formId":1,"orderId":83,
            "optionList":[{"id":252,"code":"N","name":"无"},{"id":253,"code":"Y","name":"有"}]
        },
        {"fieldName":"publicAdType","fieldDesc":"公共区域广告类型选择（含播音）（多选）","fieldFormat":"checkbox","relationData":null,"indicator":"publicResource","formId":1,"orderId":84,
            "optionList":[{"id":260,"code":"A","name":"停车场广告"},
                {"id":261,"code":"B","name":"商场幕墙广告"},
                {"id":262,"code":"C","name":"商场门廊广告"},
                {"id":263,"code":"D","name":"商场外立面"},
                {"id":264,"code":"E","name":"商场大厅广告"},
                {"id":265,"code":"F","name":"商场立柱广告"},
                {"id":266,"code":"G","name":"楼层间广告"},
                {"id":267,"code":"H","name":"电梯广告"},
                {"id":268,"code":"I","name":"过道吊旗"},
                {"id":269,"code":"J","name":"安全门广告"},
                {"id":270,"code":"K","name":"播音广告"},
                {"id":271,"code":"L","name":"LED大屏广告"},
            ]
        },
        {"fieldName":"brandSponsorType","fieldDesc":"品牌赞助广告分类（多选）","fieldFormat":"checkbox","relationData":null,"indicator":"publicResource","formId":1,"orderId":85,
            "optionList":[{"id":254,"code":"A","name":"观光梯地板"},
                {"id":255,"code":"B","name":"公区地毯"},
                {"id":256,"code":"C","name":"扶梯温馨提醒"},
                {"id":257,"code":"D","name":"收银信封"},
                {"id":258,"code":"E","name":"商场商户排名公示"},
                {"id":259,"code":"Z","name":"其他（填写）"},
            ]
        }
    ];

    $scope.maxSundryId=260;
    $scope.pubFieldDel = function(index) {
        $scope.publicFieldList.splice(index,1);
    };
    $scope.sundrySave = function(sundry,temp) {
        sundry.id = $scope.maxSundryId++;
        sundry.name = temp;
    };
    $scope.sundryDel = function(field,sundryId) {
        for(var i=0;i<field.optionList.length;i++){
            var s = field.optionList[i];
            if(s.id == sundryId){
                field.optionList.splice(i,1);
                break;
            }
        }
    };

    $scope.oneAtATime = false;
    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
    $scope.addItem = function(field) {
        field.optionList.push({"id":0,"code":"","name":""});
    };

    $scope.surveyFieldDel = function(index) {
        $scope.surveyFieldList.splice(index,1);
    };

    $scope.exhibitionSurveyInit = function(){
        $log.info($scope.surveyFieldList);
    }

    $scope.exhibitionSurveyForm = {
        "id":1,"formName":"展厅问卷模版v1.0","exhibitionDesc":"展厅默认模版","brandDesc":"慕斯","categoryDesc":"家居","updateDate":"2017-02-16 23:35:14"
    };

    $scope.firstChange=true;
    $scope.grandchildDataList = [];
    $scope.tempList = [
        {"id": 2,"name": "圣象地板"},
        {"id": 8,"name": "慕斯床垫"},
        {"id": 17,"name": "顾家沙发"},
        {"id": 19,"name": "卡萨帝"},
        {"id": 0,"name": ""}
    ];
    $scope.childChange = function(idx,childId) {
        //$log.info("--childId1="+childId);
        var optionList = $scope.surveyFieldList[idx].optionList;   //[圣象,慕斯]

        if(!$scope.firstChange){
            for(var i=0;i<$scope.tempList.length;i++){
                if(childId==$scope.tempList[i].id){
                    childId = $scope.tempList[i-1].id;
                }
            }
        }
        //$log.info("--childId2="+childId);
        $scope.firstChange=false;
        $scope.grandchildDataList = [];
        for(var i=0;i<optionList.length;i++){
            for(var j=0;j<optionList[i].childDataList.length;j++) {
                if (optionList[i].childDataList[j].id == childId) {
                    $scope.grandchildDataList =optionList[i].childDataList[j].grandchildDataList;
                    return;
                }
            }
        }
    }

    $scope.surveyFieldList = [
        {
            "fieldName": "categoryMain",
            "fieldDesc": "展厅所属于一级品类（大类）",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": "d_category",
            "formId": 1,
            "orderId": 1,
            "optionList": [
                {
                    "id": 1,
                    "code": "1",
                    "name": "家具",
                    "type": "categoryMain",
                    "childDataList": null
                },
                {
                    "id": 2,
                    "code": "2",
                    "name": "建材",
                    "type": "categoryMain",
                    "childDataList": null
                },
                {
                    "id": 3,
                    "code": "3",
                    "name": "家居饰品",
                    "type": "categoryMain",
                    "childDataList": null
                },
                {
                    "id": 4,
                    "code": "4",
                    "name": "电器",
                    "type": "categoryMain",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "categoryFunction",
            "fieldDesc": "展厅所属于品类（二级）功能",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": "d_category",
            "formId": 1,
            "orderId": 2,
            "optionList": [
                {
                    "id": 11,
                    "code": "11",
                    "name": "客厅家具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 12,
                    "code": "12",
                    "name": "寝具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 13,
                    "code": "13",
                    "name": "儿童家具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 14,
                    "code": "14",
                    "name": "综合家具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 15,
                    "code": "15",
                    "name": "办公家具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 16,
                    "code": "16",
                    "name": "地板",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 17,
                    "code": "17",
                    "name": "室外家具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 18,
                    "code": "18",
                    "name": "定制家居",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 19,
                    "code": "19",
                    "name": "定制移门衣柜",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 20,
                    "code": "20",
                    "name": "壁纸、壁布",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 21,
                    "code": "21",
                    "name": "橱柜",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 22,
                    "code": "22",
                    "name": "硅藻泥",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 23,
                    "code": "23",
                    "name": "灯具",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 24,
                    "code": "24",
                    "name": "饰品",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 25,
                    "code": "25",
                    "name": "瓷砖",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 26,
                    "code": "26",
                    "name": "石材",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 27,
                    "code": "27",
                    "name": "涂料",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 28,
                    "code": "28",
                    "name": "榻榻米",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 29,
                    "code": "29",
                    "name": "地暖",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 30,
                    "code": "30",
                    "name": "净水",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 31,
                    "code": "31",
                    "name": "厨房电器",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 32,
                    "code": "32",
                    "name": "热水器",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 33,
                    "code": "33",
                    "name": "音响设备",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 34,
                    "code": "34",
                    "name": "地毯",
                    "type": "categoryFunction",
                    "childDataList": null
                },
                {
                    "id": 35,
                    "code": "35",
                    "name": "其他",
                    "type": "categoryFunction",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "categoryMaterial",
            "fieldDesc": "展厅所属于品类（二级）材质",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": "d_category",
            "formId": 1,
            "orderId": 3,
            "optionList": [
                {
                    "id": 36,
                    "code": "36",
                    "name": "皮布结合",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 37,
                    "code": "37",
                    "name": "皮质",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 38,
                    "code": "38",
                    "name": "布艺",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 39,
                    "code": "39",
                    "name": "藤竹",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 40,
                    "code": "40",
                    "name": "红木",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 41,
                    "code": "41",
                    "name": "实木",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 42,
                    "code": "42",
                    "name": "板式",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 43,
                    "code": "43",
                    "name": "板木",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 44,
                    "code": "44",
                    "name": "石材",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 45,
                    "code": "45",
                    "name": "玻璃",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 46,
                    "code": "46",
                    "name": "塑料",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 47,
                    "code": "47",
                    "name": "棕床垫",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 48,
                    "code": "48",
                    "name": "弹簧床垫",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 49,
                    "code": "49",
                    "name": "乳胶床垫",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 50,
                    "code": "50",
                    "name": "3D面料床垫",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 51,
                    "code": "51",
                    "name": "电动",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 52,
                    "code": "52",
                    "name": "塑胶",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 53,
                    "code": "53",
                    "name": "实木复合",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 54,
                    "code": "54",
                    "name": "强化复合",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 55,
                    "code": "55",
                    "name": "金属家具",
                    "type": "categoryMaterial",
                    "childDataList": null
                },
                {
                    "id": 56,
                    "code": "56",
                    "name": "其他",
                    "type": "categoryMaterial",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "categoryStyle",
            "fieldDesc": "展厅所属于品类（二级）风格",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": "d_category",
            "formId": 1,
            "orderId": 4,
            "optionList": [
                {
                    "id": 57,
                    "code": "57",
                    "name": "中式",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 58,
                    "code": "58",
                    "name": "新中式",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 59,
                    "code": "59",
                    "name": "简欧风格",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 60,
                    "code": "60",
                    "name": "欧式风格",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 61,
                    "code": "61",
                    "name": "功能性家具",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 62,
                    "code": "62",
                    "name": "东南亚风格",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 63,
                    "code": "63",
                    "name": "美式风格",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 64,
                    "code": "64",
                    "name": "美式风格",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 65,
                    "code": "65",
                    "name": "地中海风格",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 66,
                    "code": "66",
                    "name": "现代简约",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 67,
                    "code": "67",
                    "name": "日式",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 68,
                    "code": "68",
                    "name": "韩式",
                    "type": "categoryStyle",
                    "childDataList": null
                },
                {
                    "id": 69,
                    "code": "69",
                    "name": "其他",
                    "type": "categoryStyle",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "categoryImport",
            "fieldDesc": "展厅所属于品类（二级）是否进口",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": "d_category",
            "formId": 1,
            "orderId": 5,
            "optionList": [
                {
                    "id": 70,
                    "code": "70",
                    "name": "纯进口",
                    "type": "categoryImport",
                    "childDataList": null
                },
                {
                    "id": 71,
                    "code": "71",
                    "name": "非进口",
                    "type": "categoryImport",
                    "childDataList": null
                },
                {
                    "id": 72,
                    "code": "72",
                    "name": "含进口和国产",
                    "type": "categoryImport",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "manufacturer",
            "fieldDesc": "品牌厂商名称",
            "fieldFormat": "select",
            "indicator": "base",
            "relationData": "d_manufacturer",
            "formId": 1,
            "orderId": 6,
            "optionList": [
                {
                    "id": 1,
                    "code": null,
                    "name": "床垫厂商A",
                    "type": null,
                    "childDataList": null
                },
                {
                    "id": 2,
                    "code": null,
                    "name": "家具厂商B",
                    "type": null,
                    "childDataList": null
                },
                {
                    "id": 3,
                    "code": null,
                    "name": "灯具厂商C",
                    "type": null,
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "businessNature",
            "fieldDesc": "展厅经营性质",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": "s_dealer",
            "formId": 1,
            "orderId": 7,
            "optionList": [
                {
                    "id": 178,
                    "code": "Y",
                    "name": "直营",
                    "type": null,
                    "childDataList": null
                },
                {
                    "id": 179,
                    "code": "N",
                    "name": "经销商",
                    "type": "select",
                    "childDataList": [
                        {
                            "id": 1,
                            "code": null,
                            "name": "经销商A",
                            "type": null,
                            "childDataList": null
                        },
                        {
                            "id": 2,
                            "code": null,
                            "name": "经销商B",
                            "type": null,
                            "childDataList": null
                        }
                    ]
                }
            ]
        },
        {
            "fieldName": "brand",
            "fieldDesc": "调研展厅的品牌",
            "fieldFormat": "select",
            "indicator": "base",
            "relationData": "d_brand",
            "formId": 1,
            "orderId": 8,
            "optionList": [
                {
                    "id": 1,
                    "code": null,
                    "name": "圣象",
                    "type": "select",
                    "childDataList": [
                        {
                            "id": 2,
                            "name": "圣象地板",
                            "grandchildDataList": [
                                {
                                    "id": 3,
                                    "name": "康树"
                                },
                                {
                                    "id": 4,
                                    "name": "安德森"
                                }
                            ]
                        }
                    ]
                },
                {
                    "id": 7,
                    "code": null,
                    "name": "慕斯",
                    "type": "select",
                    "childDataList": [
                        {
                            "id": 8,
                            "name": "慕斯床垫",
                            "grandchildDataList": [
                                {
                                    "id": 9,
                                    "name": "慕斯.歌蒂娅"
                                },
                                {
                                    "id": 10,
                                    "name": "慕斯.凯奇"
                                },
                                {
                                    "id": 11,
                                    "name": "慕斯.3D"
                                },
                                {
                                    "id": 12,
                                    "name": "慕斯.0769"
                                },
                                {
                                    "id": 13,
                                    "name": "慕斯.V6"
                                },
                                {
                                    "id": 14,
                                    "name": "慕斯.苏菲娜"
                                },
                                {
                                    "id": 15,
                                    "name": "慕斯.爱迪奇"
                                }
                            ]
                        }
                    ]
                },
                {
                    "id": 16,
                    "code": null,
                    "name": "顾家",
                    "type": "select",
                    "childDataList": [
                        {
                            "id": 17,
                            "name": "顾家沙发",
                            "grandchildDataList": []
                        }
                    ]
                },
                {
                    "id": 18,
                    "code": null,
                    "name": "海尔",
                    "type": "select",
                    "childDataList": [
                        {
                            "id": 19,
                            "name": "卡萨帝",
                            "grandchildDataList": [
                                {
                                    "id": 20,
                                    "name": "卡萨帝冰箱"
                                },
                                {
                                    "id": 21,
                                    "name": "卡萨帝洗衣机"
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        {
            "fieldName": "standAloneStore",
            "fieldDesc": "是否独立店面",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 9,
            "optionList": [
                {
                    "id": 178,
                    "code": "Y",
                    "name": "独立店面",
                    "type": "standAloneStore",
                    "childDataList": null
                },
                {
                    "id": 179,
                    "code": "N",
                    "name": "非独立店面",
                    "type": "standAloneStore",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "exhibitionCity",
            "fieldDesc": "商场所在省市区",
            "fieldFormat": "input",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 10,
            "optionList": []
        },
        {
            "fieldName": "exhibitionAddress",
            "fieldDesc": "详细地址",
            "fieldFormat": "input",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 11,
            "optionList": []
        },
        {
            "fieldName": "exhibitionBusinessHours",
            "fieldDesc": "营业时间",
            "fieldFormat": "input",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 12,
            "optionList": []
        },
        {
            "fieldName": "floor",
            "fieldDesc": "展厅主入口所在楼层",
            "fieldFormat": "number",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 13,
            "optionList": []
        },
        {
            "fieldName": "floorPosition",
            "fieldDesc": "在正厅或者侧厅位置",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 14,
            "optionList": [
                {
                    "id": 3,
                    "code": "CT",
                    "name": "侧厅（与侧面对应）",
                    "type": "floorPosition",
                    "childDataList": null
                },
                {
                    "id": 4,
                    "code": "ZT",
                    "name": "正厅（与正门或总服务台对应）",
                    "type": "floorPosition",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "peripheryFacility",
            "fieldDesc": "距离展厅十米范围内，是否有相应设施（不定项）",
            "fieldFormat": "checkbox",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 15,
            "optionList": [
                {
                    "id": 272,
                    "code": "A",
                    "name": "卫生间",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 273,
                    "code": "B",
                    "name": "收银台（服务台）",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 274,
                    "code": "C",
                    "name": "楼梯",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 275,
                    "code": "D",
                    "name": "自动扶梯",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 276,
                    "code": "E",
                    "name": "餐厅",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 277,
                    "code": "F",
                    "name": "观光梯",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 278,
                    "code": "G",
                    "name": "楼层平台",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 279,
                    "code": "H",
                    "name": "顾客休息区",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 280,
                    "code": "I",
                    "name": "装修展位",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 281,
                    "code": "J",
                    "name": "空场展位(闭灯）",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 282,
                    "code": "K",
                    "name": "安全门",
                    "type": "peripheryFacility",
                    "childDataList": null
                },
                {
                    "id": 283,
                    "code": "L",
                    "name": "无以上设施",
                    "type": "peripheryFacility",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "exhibitionArea",
            "fieldDesc": "预估展厅大致的面积",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 16,
            "optionList": [
                {
                    "id": 21,
                    "code": "A",
                    "name": "100㎡以下",
                    "type": "exhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 22,
                    "code": "B",
                    "name": "100-300㎡",
                    "type": "exhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 23,
                    "code": "C",
                    "name": "300-500㎡",
                    "type": "exhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 24,
                    "code": "D",
                    "name": "500-1000㎡",
                    "type": "exhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 25,
                    "code": "E",
                    "name": "1000㎡以上",
                    "type": "exhibitionArea",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "exhibitionEntrance",
            "fieldDesc": "展厅入口数量",
            "fieldFormat": "number",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 17,
            "optionList": []
        },
        {
            "fieldName": "exhibitionStatus",
            "fieldDesc": "展厅状态",
            "fieldFormat": "radio",
            "indicator": "base",
            "relationData": null,
            "formId": 1,
            "orderId": 18,
            "optionList": [
                {
                    "id": 180,
                    "code": "A",
                    "name": "正常营业",
                    "type": "exhibitionStatus",
                    "childDataList": null
                },
                {
                    "id": 181,
                    "code": "B",
                    "name": "营业，无营业员",
                    "type": "exhibitionStatus",
                    "childDataList": null
                },
                {
                    "id": 182,
                    "code": "C",
                    "name": "暂停营业",
                    "type": "exhibitionStatus",
                    "childDataList": null
                },
                {
                    "id": 183,
                    "code": "D",
                    "name": "黑灯，无说明",
                    "type": "exhibitionStatus",
                    "childDataList": null
                },
                {
                    "id": 184,
                    "code": "E",
                    "name": "开灯，无营业员",
                    "type": "exhibitionStatus",
                    "childDataList": null
                },
                {
                    "id": 185,
                    "code": "F",
                    "name": "围挡状态",
                    "type": "exhibitionStatus",
                    "childDataList": null
                },
                {
                    "id": 186,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "exhibitionStatus",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "outerWall",
            "fieldDesc": "展厅外墙展示",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 20,
            "optionList": [
                {
                    "id": 187,
                    "code": "A",
                    "name": "无形象展示",
                    "type": "outerWall",
                    "childDataList": null
                },
                {
                    "id": 188,
                    "code": "B",
                    "name": "有造型橱窗",
                    "type": "outerWall",
                    "childDataList": null
                },
                {
                    "id": 189,
                    "code": "C",
                    "name": "透明，见样品背面",
                    "type": "outerWall",
                    "childDataList": null
                },
                {
                    "id": 190,
                    "code": "D",
                    "name": "透明，见样品正面",
                    "type": "outerWall",
                    "childDataList": null
                },
                {
                    "id": 191,
                    "code": "E",
                    "name": "灯箱广告",
                    "type": "outerWall",
                    "childDataList": null
                },
                {
                    "id": 192,
                    "code": "F",
                    "name": "非亮面广告",
                    "type": "outerWall",
                    "childDataList": null
                },
                {
                    "id": 193,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "outerWall",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "workbenchStyle",
            "fieldDesc": "工作区风格",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 21,
            "optionList": [
                {
                    "id": 306,
                    "code": "A",
                    "name": "工作台设计与展厅整体风格不符合",
                    "type": "workbenchStyle",
                    "childDataList": null
                },
                {
                    "id": 307,
                    "code": "B",
                    "name": "洽谈区设计与展厅整体风格不符合",
                    "type": "workbenchStyle",
                    "childDataList": null
                },
                {
                    "id": 308,
                    "code": "C",
                    "name": "设计区设计与展厅整体风格不符合",
                    "type": "workbenchStyle",
                    "childDataList": null
                },
                {
                    "id": 309,
                    "code": "D",
                    "name": "设计良好",
                    "type": "workbenchStyle",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "workbenchImage",
            "fieldDesc": "门头、主要展区情景片（拍照即可，3-5）",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 22,
            "optionList": [
                {
                    "id": 310,
                    "code": "A",
                    "name": "门头",
                    "type": "workbenchImage",
                    "childDataList": null
                },
                {
                    "id": 311,
                    "code": "B",
                    "name": "洽谈区",
                    "type": "workbenchImage",
                    "childDataList": null
                },
                {
                    "id": 312,
                    "code": "C",
                    "name": "工作台",
                    "type": "workbenchImage",
                    "childDataList": null
                },
                {
                    "id": 313,
                    "code": "D",
                    "name": "主要情景间（1-2张）",
                    "type": "workbenchImage",
                    "childDataList": null
                },
                {
                    "id": 314,
                    "code": "E",
                    "name": "背景墙（若有）",
                    "type": "workbenchImage",
                    "childDataList": null
                },
                {
                    "id": 315,
                    "code": "F",
                    "name": "设计区（若有）",
                    "type": "workbenchImage",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "brandPublicityType",
            "fieldDesc": "展厅品牌宣传形式",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 23,
            "optionList": [
                {
                    "id": 194,
                    "code": "A",
                    "name": "门头",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 195,
                    "code": "B",
                    "name": "灯箱广告",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 196,
                    "code": "C",
                    "name": "立牌",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 197,
                    "code": "D",
                    "name": "LED屏幕（含电视）",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 198,
                    "code": "E",
                    "name": "工作台",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 199,
                    "code": "F",
                    "name": "背景墙",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 200,
                    "code": "G",
                    "name": "门口地贴",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 201,
                    "code": "H",
                    "name": "投影灯",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 202,
                    "code": "I",
                    "name": "腰线",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 203,
                    "code": "J",
                    "name": "无",
                    "type": "brandPublicityType",
                    "childDataList": null
                },
                {
                    "id": 204,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "brandPublicityType",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "brandStained",
            "fieldDesc": "品牌是否存在不亮或有污损的情况",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 24,
            "optionList": [
                {
                    "id": 205,
                    "code": "A",
                    "name": "门头",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 206,
                    "code": "B",
                    "name": "灯箱广告",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 207,
                    "code": "C",
                    "name": "立牌",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 208,
                    "code": "D",
                    "name": "LED屏幕（含电视）",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 209,
                    "code": "E",
                    "name": "工作台",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 210,
                    "code": "F",
                    "name": "背景墙",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 211,
                    "code": "G",
                    "name": "门口地贴",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 212,
                    "code": "H",
                    "name": "投影灯",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 213,
                    "code": "I",
                    "name": "腰线",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 214,
                    "code": "J",
                    "name": "无",
                    "type": "brandStained",
                    "childDataList": null
                },
                {
                    "id": 215,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "brandStained",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "spokespersonImage",
            "fieldDesc": "代言人形象体现在",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 25,
            "optionList": [
                {
                    "id": 216,
                    "code": "A",
                    "name": "立牌",
                    "type": "spokespersonImage",
                    "childDataList": null
                },
                {
                    "id": 217,
                    "code": "B",
                    "name": "灯箱广告",
                    "type": "spokespersonImage",
                    "childDataList": null
                },
                {
                    "id": 218,
                    "code": "C",
                    "name": "海报",
                    "type": "spokespersonImage",
                    "childDataList": null
                },
                {
                    "id": 219,
                    "code": "D",
                    "name": "LED屏幕（含电视）",
                    "type": "spokespersonImage",
                    "childDataList": null
                },
                {
                    "id": 220,
                    "code": "E",
                    "name": "地贴",
                    "type": "spokespersonImage",
                    "childDataList": null
                },
                {
                    "id": 221,
                    "code": "F",
                    "name": "无",
                    "type": "spokespersonImage",
                    "childDataList": null
                },
                {
                    "id": 222,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "spokespersonImage",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "hygiene",
            "fieldDesc": "展厅的卫生情况",
            "fieldFormat": "checkbox",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 26,
            "optionList": [
                {
                    "id": 284,
                    "code": "A",
                    "name": "绿植未清洁打理",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 285,
                    "code": "B",
                    "name": "地面有垃圾",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 286,
                    "code": "C",
                    "name": "地面有杂物",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 287,
                    "code": "D",
                    "name": "样品积灰",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 288,
                    "code": "E",
                    "name": "样品损坏",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 289,
                    "code": "F",
                    "name": "卫生用品乱放",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 290,
                    "code": "G",
                    "name": "设施破损",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 291,
                    "code": "H",
                    "name": "个人物品乱放",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 292,
                    "code": "I",
                    "name": "外墙污渍",
                    "type": "hygiene",
                    "childDataList": null
                },
                {
                    "id": 293,
                    "code": "J",
                    "name": "卫生良好",
                    "type": "hygiene",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "lighting",
            "fieldDesc": "展厅内灯光",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 27,
            "optionList": [
                {
                    "id": 52,
                    "code": "A",
                    "name": "全开明亮",
                    "type": "lighting",
                    "childDataList": null
                },
                {
                    "id": 53,
                    "code": "B",
                    "name": "全开，但是有明显昏暗区域",
                    "type": "lighting",
                    "childDataList": null
                },
                {
                    "id": 54,
                    "code": "C",
                    "name": "部分区域未开灯",
                    "type": "lighting",
                    "childDataList": null
                },
                {
                    "id": 55,
                    "code": "D",
                    "name": "熄灯",
                    "type": "lighting",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "smell",
            "fieldDesc": "是否有异味或者刺鼻香味",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 28,
            "optionList": [
                {
                    "id": 60,
                    "code": "X",
                    "name": "有宜人香氛",
                    "type": "smell",
                    "childDataList": null
                },
                {
                    "id": 61,
                    "code": "W",
                    "name": "无异味或者刺鼻香味",
                    "type": "smell",
                    "childDataList": null
                },
                {
                    "id": 62,
                    "code": "C",
                    "name": "有异味或者刺鼻香味",
                    "type": "smell",
                    "childDataList": null
                },
                {
                    "id": 63,
                    "code": "Y",
                    "name": "其它香味",
                    "type": "smell",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "workbenchHygiene",
            "fieldDesc": "工作台是否整洁，无杂物",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 29,
            "optionList": [
                {
                    "id": 64,
                    "code": "W",
                    "name": "无工作台",
                    "type": "workbenchHygiene",
                    "childDataList": null
                },
                {
                    "id": 65,
                    "code": "G",
                    "name": "干净整洁",
                    "type": "workbenchHygiene",
                    "childDataList": null
                },
                {
                    "id": 66,
                    "code": "Z",
                    "name": "有杂物",
                    "type": "workbenchHygiene",
                    "childDataList": null
                },
                {
                    "id": 67,
                    "code": "B",
                    "name": "不整洁",
                    "type": "workbenchHygiene",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "discussionAreas",
            "fieldDesc": "洽谈区是否整洁，无杂物",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 30,
            "optionList": [
                {
                    "id": 72,
                    "code": "W",
                    "name": "无独立洽谈区",
                    "type": "discussionAreas",
                    "childDataList": null
                },
                {
                    "id": 73,
                    "code": "G",
                    "name": "干净整洁",
                    "type": "discussionAreas",
                    "childDataList": null
                },
                {
                    "id": 74,
                    "code": "Z",
                    "name": "有杂物",
                    "type": "discussionAreas",
                    "childDataList": null
                },
                {
                    "id": 75,
                    "code": "B",
                    "name": "不整洁",
                    "type": "discussionAreas",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "backgroundWallHygiene",
            "fieldDesc": "背景墙是否整洁，品牌logo无破损",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 31,
            "optionList": [
                {
                    "id": 76,
                    "code": "W",
                    "name": "无背景墙",
                    "type": "backgroundWallHygiene",
                    "childDataList": null
                },
                {
                    "id": 77,
                    "code": "G",
                    "name": "干净整洁",
                    "type": "backgroundWallHygiene",
                    "childDataList": null
                },
                {
                    "id": 78,
                    "code": "P",
                    "name": "有破损",
                    "type": "backgroundWallHygiene",
                    "childDataList": null
                },
                {
                    "id": 79,
                    "code": "J",
                    "name": "有污迹",
                    "type": "backgroundWallHygiene",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "designAreaHygiene",
            "fieldDesc": "设计区是否整洁，无杂物",
            "fieldFormat": "radio",
            "indicator": "environment",
            "relationData": null,
            "formId": 1,
            "orderId": 32,
            "optionList": [
                {
                    "id": 80,
                    "code": "W",
                    "name": "无设计区",
                    "type": "designAreaHygiene",
                    "childDataList": null
                },
                {
                    "id": 81,
                    "code": "G",
                    "name": "干净整洁",
                    "type": "designAreaHygiene",
                    "childDataList": null
                },
                {
                    "id": 82,
                    "code": "Z",
                    "name": "有杂物",
                    "type": "designAreaHygiene",
                    "childDataList": null
                },
                {
                    "id": 83,
                    "code": "B",
                    "name": "不整洁",
                    "type": "designAreaHygiene",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "shopEmployeesNumber",
            "fieldDesc": "营业员数量",
            "fieldFormat": "number",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 40,
            "optionList": []
        },
        {
            "fieldName": "designer",
            "fieldDesc": "是否有设计师",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": "exist",
            "formId": 1,
            "orderId": 41,
            "optionList": [
                {
                    "id": 252,
                    "code": "N",
                    "name": "无",
                    "type": "exist",
                    "childDataList": null
                },
                {
                    "id": 253,
                    "code": "Y",
                    "name": "有",
                    "type": "exist",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "gender",
            "fieldDesc": "接待人员的性别",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 42,
            "optionList": [
                {
                    "id": 135,
                    "code": "M",
                    "name": "男",
                    "type": "gender",
                    "childDataList": null
                },
                {
                    "id": 136,
                    "code": "F",
                    "name": "女",
                    "type": "gender",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "age",
            "fieldDesc": "接待人员的年龄（大致年龄段）",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 43,
            "optionList": [
                {
                    "id": 137,
                    "code": "A",
                    "name": "20-35岁",
                    "type": "age",
                    "childDataList": null
                },
                {
                    "id": 138,
                    "code": "B",
                    "name": "35-45岁",
                    "type": "age",
                    "childDataList": null
                },
                {
                    "id": 139,
                    "code": "C",
                    "name": "45以上",
                    "type": "age",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "shopEmployeesImage",
            "fieldDesc": "人员形象",
            "fieldFormat": "checkbox",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 44,
            "optionList": [
                {
                    "id": 109,
                    "code": "A",
                    "name": "发型不符合职业要求",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 110,
                    "code": "B",
                    "name": "未化妆",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 111,
                    "code": "C",
                    "name": "服装",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 112,
                    "code": "D",
                    "name": "指甲",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 113,
                    "code": "E",
                    "name": "胡须",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 114,
                    "code": "F",
                    "name": "鞋子",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 115,
                    "code": "G",
                    "name": "工牌",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 316,
                    "code": "H",
                    "name": " 形象良好",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                },
                {
                    "id": 317,
                    "code": "Z",
                    "name": " 其他",
                    "type": "shopEmployeesImage",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "welcomeGuest",
            "fieldDesc": "展厅是否迎宾",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 45,
            "optionList": [
                {
                    "id": 318,
                    "code": "A",
                    "name": "门口站立迎宾，使用礼貌用语",
                    "type": "welcomeGuest",
                    "childDataList": null
                },
                {
                    "id": 319,
                    "code": "B",
                    "name": "展厅内问好，使用礼貌用语",
                    "type": "welcomeGuest",
                    "childDataList": null
                },
                {
                    "id": 320,
                    "code": "C",
                    "name": "没有使用礼貌用语",
                    "type": "welcomeGuest",
                    "childDataList": null
                },
                {
                    "id": 321,
                    "code": "D",
                    "name": "无迎宾问好",
                    "type": "welcomeGuest",
                    "childDataList": null
                },
                {
                    "id": 322,
                    "code": "E",
                    "name": "无迎宾问好，且态度差",
                    "type": "welcomeGuest",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "seeOffGuest",
            "fieldDesc": "展厅是否送宾",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 46,
            "optionList": [
                {
                    "id": 323,
                    "code": "A",
                    "name": "门口站立送宾，使用礼貌用语",
                    "type": "seeOffGuest",
                    "childDataList": null
                },
                {
                    "id": 324,
                    "code": "B",
                    "name": "展厅内送宾，使用礼貌用语",
                    "type": "seeOffGuest",
                    "childDataList": null
                },
                {
                    "id": 325,
                    "code": "C",
                    "name": "没有使用礼貌用语",
                    "type": "seeOffGuest",
                    "childDataList": null
                },
                {
                    "id": 326,
                    "code": "D",
                    "name": "无送宾声音",
                    "type": "seeOffGuest",
                    "childDataList": null
                },
                {
                    "id": 327,
                    "code": "E",
                    "name": "无送宾声音，且态度差",
                    "type": "seeOffGuest",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "productionIntroduce",
            "fieldDesc": "产品介绍",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 47,
            "optionList": [
                {
                    "id": 119,
                    "code": "Z",
                    "name": "主动专业介绍",
                    "type": "productionIntroduce",
                    "childDataList": null
                },
                {
                    "id": 120,
                    "code": "J",
                    "name": "问答式介绍",
                    "type": "productionIntroduce",
                    "childDataList": null
                },
                {
                    "id": 121,
                    "code": "B",
                    "name": "对产品品牌不熟悉",
                    "type": "productionIntroduce",
                    "childDataList": null
                },
                {
                    "id": 122,
                    "code": "W",
                    "name": "无介绍",
                    "type": "productionIntroduce",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "smileHello",
            "fieldDesc": "接待人员介绍过程是否友好",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 48,
            "optionList": [
                {
                    "id": 123,
                    "code": "R",
                    "name": "热情有微笑",
                    "type": "smileHello",
                    "childDataList": null
                },
                {
                    "id": 124,
                    "code": "J",
                    "name": "接待但无微笑",
                    "type": "smileHello",
                    "childDataList": null
                },
                {
                    "id": 125,
                    "code": "W",
                    "name": "无微笑态度不好",
                    "type": "smileHello",
                    "childDataList": null
                },
                {
                    "id": 126,
                    "code": "B",
                    "name": "不理睬顾客",
                    "type": "smileHello",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "violations",
            "fieldDesc": "展厅内存在违规行为",
            "fieldFormat": "checkbox",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 49,
            "optionList": [
                {
                    "id": 127,
                    "code": "C",
                    "name": "串展厅",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 128,
                    "code": "J",
                    "name": "聚众聊天",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 129,
                    "code": "F",
                    "name": "展厅吃饭",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 130,
                    "code": "D",
                    "name": "大声打电话",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 131,
                    "code": "S",
                    "name": "大声说笑",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 132,
                    "code": "K",
                    "name": "公开看视频",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 133,
                    "code": "P",
                    "name": "有朋友家属",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 134,
                    "code": "G",
                    "name": "接待时玩手机",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 328,
                    "code": "W",
                    "name": "无",
                    "type": "violations",
                    "childDataList": null
                },
                {
                    "id": 329,
                    "code": "Z",
                    "name": "其他",
                    "type": "violations",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "violationWords",
            "fieldDesc": "展厅内存在违规用语",
            "fieldFormat": "radio",
            "indicator": "reception",
            "relationData": null,
            "formId": 1,
            "orderId": 50,
            "optionList": [
                {
                    "id": 223,
                    "code": "A",
                    "name": "“你自己看吧。”",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 224,
                    "code": "B",
                    "name": "“不可能出现这种问题。”",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 225,
                    "code": "C",
                    "name": "“这肯定不是我们的原因”",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 226,
                    "code": "D",
                    "name": "“我不知道。”",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 227,
                    "code": "E",
                    "name": "“这么简单的问题你也不明白。”",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 228,
                    "code": "F",
                    "name": "“我只负责卖商品，不负责……….”",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 229,
                    "code": "G",
                    "name": "“产品的质量都差不多，没什么可挑的。”\t",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 230,
                    "code": "H",
                    "name": "无\t",
                    "type": "violationWords",
                    "childDataList": null
                },
                {
                    "id": 231,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "violationWords",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "guestSnack",
            "fieldDesc": "展厅提供的零食、水果",
            "fieldFormat": "checkbox",
            "indicator": "service",
            "relationData": null,
            "formId": 1,
            "orderId": 60,
            "optionList": [
                {
                    "id": 143,
                    "code": "T",
                    "name": "糖果",
                    "type": "guestSnack",
                    "childDataList": null
                },
                {
                    "id": 144,
                    "code": "X",
                    "name": "小吃",
                    "type": "guestSnack",
                    "childDataList": null
                },
                {
                    "id": 145,
                    "code": "S",
                    "name": "水果",
                    "type": "guestSnack",
                    "childDataList": null
                },
                {
                    "id": 146,
                    "code": "W",
                    "name": "无",
                    "type": "guestSnack",
                    "childDataList": null
                },
                {
                    "id": 147,
                    "code": "Z",
                    "name": "其它食品（填写）",
                    "type": "guestSnack",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "guestDrink",
            "fieldDesc": "展厅提供的茶水、饮料",
            "fieldFormat": "checkbox",
            "indicator": "service",
            "relationData": null,
            "formId": 1,
            "orderId": 61,
            "optionList": [
                {
                    "id": 148,
                    "code": "W",
                    "name": "无",
                    "type": "guestDrink",
                    "childDataList": null
                },
                {
                    "id": 149,
                    "code": "B",
                    "name": "白开水",
                    "type": "guestDrink",
                    "childDataList": null
                },
                {
                    "id": 150,
                    "code": "K",
                    "name": "咖啡",
                    "type": "guestDrink",
                    "childDataList": null
                },
                {
                    "id": 151,
                    "code": "G",
                    "name": "果汁",
                    "type": "guestDrink",
                    "childDataList": null
                },
                {
                    "id": 152,
                    "code": "C",
                    "name": "茶水",
                    "type": "guestDrink",
                    "childDataList": null
                },
                {
                    "id": 330,
                    "code": "Y",
                    "name": "瓶装饮料",
                    "type": "guestDrink",
                    "childDataList": null
                },
                {
                    "id": 331,
                    "code": "Z",
                    "name": "其它饮料（填写）",
                    "type": "guestDrink",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "guestInOut",
            "fieldDesc": "在调研期间（进入-离开），是否看到有其他顾客进入",
            "fieldFormat": "radio",
            "indicator": "passengerFlow",
            "relationData": "yn",
            "formId": 1,
            "orderId": 62,
            "optionList": [
                {
                    "id": 1,
                    "code": "Y",
                    "name": "是",
                    "type": "yn",
                    "childDataList": null
                },
                {
                    "id": 2,
                    "code": "N",
                    "name": "否",
                    "type": "yn",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "promotionType",
            "fieldDesc": "依据促销的范围划分",
            "fieldFormat": "radio",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 63,
            "optionList": [
                {
                    "id": 153,
                    "code": "W",
                    "name": "无促销",
                    "type": "promotionType",
                    "childDataList": null
                },
                {
                    "id": 154,
                    "code": "L",
                    "name": "联盟促销",
                    "type": "promotionType",
                    "childDataList": null
                },
                {
                    "id": 155,
                    "code": "S",
                    "name": "商场促销",
                    "type": "promotionType",
                    "childDataList": null
                },
                {
                    "id": 156,
                    "code": "G",
                    "name": "个体促销",
                    "type": "promotionType",
                    "childDataList": null
                },
                {
                    "id": 157,
                    "code": "P",
                    "name": "品牌促销",
                    "type": "promotionType",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "promotionType2",
            "fieldDesc": "依据促销的形式划分",
            "fieldFormat": "radio",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 64,
            "optionList": []
        },
        {
            "fieldName": "promotionStyle",
            "fieldDesc": "展厅的主要促销形式",
            "fieldFormat": "radio",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 65,
            "optionList": [
                {
                    "id": 332,
                    "code": "A",
                    "name": "无促销",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 333,
                    "code": "B",
                    "name": "抽奖",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 334,
                    "code": "C",
                    "name": "买赠",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 335,
                    "code": "D",
                    "name": "返现",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 336,
                    "code": "E",
                    "name": "服务性促销（例如以旧换新）",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 337,
                    "code": "F",
                    "name": "折扣",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 338,
                    "code": "G",
                    "name": "赠券",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 339,
                    "code": "H",
                    "name": "套餐",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 340,
                    "code": "I",
                    "name": "统一价",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 341,
                    "code": "J",
                    "name": "会员促销",
                    "type": "promotionStyle",
                    "childDataList": null
                },
                {
                    "id": 342,
                    "code": "Z",
                    "name": "其它（填写）",
                    "type": "promotionStyle",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "specialOffer",
            "fieldDesc": "是否有标注的特价款，最低折扣",
            "fieldFormat": "radio",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 66,
            "optionList": [
                {
                    "id": 165,
                    "code": "A",
                    "name": "9折及以上",
                    "type": "specialOffer",
                    "childDataList": null
                },
                {
                    "id": 166,
                    "code": "B",
                    "name": "8折（含）-9折",
                    "type": "specialOffer",
                    "childDataList": null
                },
                {
                    "id": 167,
                    "code": "C",
                    "name": "7折（含）-8折",
                    "type": "specialOffer",
                    "childDataList": null
                },
                {
                    "id": 168,
                    "code": "D",
                    "name": "6折（含）-7折",
                    "type": "specialOffer",
                    "childDataList": null
                },
                {
                    "id": 169,
                    "code": "E",
                    "name": "5折（含）-6折",
                    "type": "specialOffer",
                    "childDataList": null
                },
                {
                    "id": 170,
                    "code": "F",
                    "name": "5折以下",
                    "type": "specialOffer",
                    "childDataList": null
                },
                {
                    "id": 171,
                    "code": "W",
                    "name": "无折扣",
                    "type": "specialOffer",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "salesPromotionMaterials",
            "fieldDesc": "展厅促销物料形式",
            "fieldFormat": "checkbox",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 68,
            "optionList": [
                {
                    "id": 294,
                    "code": "A",
                    "name": "无促销物料",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 295,
                    "code": "B",
                    "name": "红地毯",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 296,
                    "code": "C",
                    "name": "地贴",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 297,
                    "code": "D",
                    "name": "门口立柱",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 298,
                    "code": "E",
                    "name": "龙门架",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 299,
                    "code": "F",
                    "name": "广告支架",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 300,
                    "code": "G",
                    "name": "门头贴纸",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 301,
                    "code": "H",
                    "name": "宣传单页",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 302,
                    "code": "I",
                    "name": "活动海报",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 303,
                    "code": "J",
                    "name": "爆炸贴",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 304,
                    "code": "K",
                    "name": "吊旗",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                },
                {
                    "id": 305,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "salesPromotionMaterials",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "promotionStyle2",
            "fieldDesc": "展厅促销物料主色调",
            "fieldFormat": "radio",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 69,
            "optionList": [
                {
                    "id": 343,
                    "code": "A",
                    "name": "红",
                    "type": "promotionStyle2",
                    "childDataList": null
                },
                {
                    "id": 344,
                    "code": "B",
                    "name": "蓝",
                    "type": "promotionStyle2",
                    "childDataList": null
                },
                {
                    "id": 345,
                    "code": "C",
                    "name": "无明确主色调",
                    "type": "promotionStyle2",
                    "childDataList": null
                },
                {
                    "id": 346,
                    "code": "D",
                    "name": "无明确主色调",
                    "type": "promotionStyle2",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "salesAvgPromotion",
            "fieldDesc": "展厅促销的平均折扣",
            "fieldFormat": "radio",
            "indicator": "promotion",
            "relationData": null,
            "formId": 1,
            "orderId": 70,
            "optionList": [
                {
                    "id": 232,
                    "code": "A",
                    "name": "无促销",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                },
                {
                    "id": 233,
                    "code": "B",
                    "name": "9折及以上",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                },
                {
                    "id": 234,
                    "code": "C",
                    "name": "8折（含）-9折",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                },
                {
                    "id": 235,
                    "code": "D",
                    "name": "7折（含）-8折",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                },
                {
                    "id": 236,
                    "code": "E",
                    "name": "6折（含）-7折",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                },
                {
                    "id": 237,
                    "code": "F",
                    "name": "5折（含）-6折",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                },
                {
                    "id": 238,
                    "code": "G",
                    "name": "5折以下",
                    "type": "salesAvgPromotion",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "exhibitVacant",
            "fieldDesc": "展厅内出样，是否存在明显空置位置",
            "fieldFormat": "radio",
            "indicator": "exhibits",
            "relationData": null,
            "formId": 1,
            "orderId": 71,
            "optionList": [
                {
                    "id": 140,
                    "code": "Y",
                    "name": "是",
                    "type": "exhibitVacant",
                    "childDataList": null
                },
                {
                    "id": 141,
                    "code": "N",
                    "name": "否",
                    "type": "exhibitVacant",
                    "childDataList": null
                },
                {
                    "id": 142,
                    "code": "J",
                    "name": "出样过于紧凑",
                    "type": "exhibitVacant",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "newProduction",
            "fieldDesc": "是否有新款商品（“新品上样”字样，或营业员介绍）",
            "fieldFormat": "radio",
            "indicator": "exhibits",
            "relationData": "yn",
            "formId": 1,
            "orderId": 72,
            "optionList": [
                {
                    "id": 1,
                    "code": "Y",
                    "name": "是",
                    "type": "yn",
                    "childDataList": null
                },
                {
                    "id": 2,
                    "code": "N",
                    "name": "否",
                    "type": "yn",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "hasWifi",
            "fieldDesc": "展厅是否有WIFI",
            "fieldFormat": "radio",
            "indicator": "service",
            "relationData": "exist",
            "formId": 1,
            "orderId": 73,
            "optionList": [
                {
                    "id": 252,
                    "code": "N",
                    "name": "无",
                    "type": "exist",
                    "childDataList": null
                },
                {
                    "id": 253,
                    "code": "Y",
                    "name": "有",
                    "type": "exist",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "customerPicWall",
            "fieldDesc": "展厅是否有顾客照片墙",
            "fieldFormat": "checkbox",
            "indicator": "service",
            "relationData": null,
            "formId": 1,
            "orderId": 74,
            "optionList": [
                {
                    "id": 241,
                    "code": "A",
                    "name": "顾客照片",
                    "type": "customerPicWall",
                    "childDataList": null
                },
                {
                    "id": 242,
                    "code": "B",
                    "name": "顾客家装修效果",
                    "type": "customerPicWall",
                    "childDataList": null
                },
                {
                    "id": 243,
                    "code": "C",
                    "name": "照片含小区信息",
                    "type": "customerPicWall",
                    "childDataList": null
                },
                {
                    "id": 244,
                    "code": "D",
                    "name": "营业员主动介绍",
                    "type": "customerPicWall",
                    "childDataList": null
                },
                {
                    "id": 245,
                    "code": "E",
                    "name": "无照片墙",
                    "type": "customerPicWall",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "featureShow",
            "fieldDesc": "展厅是否有特色展示",
            "fieldFormat": "radio",
            "indicator": "service",
            "relationData": null,
            "formId": 1,
            "orderId": 75,
            "optionList": [
                {
                    "id": 246,
                    "code": "A",
                    "name": "产品设计专利",
                    "type": "featureShow",
                    "childDataList": null
                },
                {
                    "id": 247,
                    "code": "B",
                    "name": "产品效果图",
                    "type": "featureShow",
                    "childDataList": null
                },
                {
                    "id": 248,
                    "code": "C",
                    "name": "产品工艺介绍",
                    "type": "featureShow",
                    "childDataList": null
                },
                {
                    "id": 249,
                    "code": "D",
                    "name": "展厅服务流程",
                    "type": "featureShow",
                    "childDataList": null
                },
                {
                    "id": 250,
                    "code": "E",
                    "name": "借助知名人物",
                    "type": "featureShow",
                    "childDataList": null
                },
                {
                    "id": 251,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "featureShow",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "memberArea",
            "fieldDesc": "会员活动区域",
            "fieldFormat": "radio",
            "indicator": "service",
            "relationData": "exist",
            "formId": 1,
            "orderId": 76,
            "optionList": [
                {
                    "id": 252,
                    "code": "N",
                    "name": "无",
                    "type": "exist",
                    "childDataList": null
                },
                {
                    "id": 253,
                    "code": "Y",
                    "name": "有",
                    "type": "exist",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "publicExhibitionExist",
            "fieldDesc": "公区摆是否有",
            "fieldFormat": "radio",
            "indicator": "publicResource",
            "relationData": "exist",
            "formId": 1,
            "orderId": 80,
            "optionList": [
                {
                    "id": 252,
                    "code": "N",
                    "name": "无",
                    "type": "exist",
                    "childDataList": null
                },
                {
                    "id": 253,
                    "code": "Y",
                    "name": "有",
                    "type": "exist",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "publicExhibitionArea",
            "fieldDesc": "公区摆展面积",
            "fieldFormat": "radio",
            "indicator": "publicResource",
            "relationData": null,
            "formId": 1,
            "orderId": 81,
            "optionList": [
                {
                    "id": 26,
                    "code": "A",
                    "name": "5㎡以下",
                    "type": "publicExhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 27,
                    "code": "B",
                    "name": "5-10㎡",
                    "type": "publicExhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 28,
                    "code": "C",
                    "name": "10-20㎡",
                    "type": "publicExhibitionArea",
                    "childDataList": null
                },
                {
                    "id": 29,
                    "code": "D",
                    "name": "20㎡以上",
                    "type": "publicExhibitionArea",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "publicExhibitionFloor",
            "fieldDesc": "公区摆展楼层",
            "fieldFormat": "number",
            "indicator": "publicResource",
            "relationData": null,
            "formId": 1,
            "orderId": 82,
            "optionList": []
        },
        {
            "fieldName": "publicExhibitionPriceTag",
            "fieldDesc": "公区摆展是否有有价签",
            "fieldFormat": "radio",
            "indicator": "publicResource",
            "relationData": "exist",
            "formId": 1,
            "orderId": 83,
            "optionList": [
                {
                    "id": 252,
                    "code": "N",
                    "name": "无",
                    "type": "exist",
                    "childDataList": null
                },
                {
                    "id": 253,
                    "code": "Y",
                    "name": "有",
                    "type": "exist",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "publicAdType",
            "fieldDesc": "公共区域广告类型选择（含播音）（多选）",
            "fieldFormat": "checkbox",
            "indicator": "publicResource",
            "relationData": null,
            "formId": 1,
            "orderId": 84,
            "optionList": [
                {
                    "id": 260,
                    "code": "A",
                    "name": "停车场广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 261,
                    "code": "B",
                    "name": "商场幕墙广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 262,
                    "code": "C",
                    "name": "商场门廊广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 263,
                    "code": "D",
                    "name": "商场外立面",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 264,
                    "code": "E",
                    "name": "商场大厅广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 265,
                    "code": "F",
                    "name": "商场立柱广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 266,
                    "code": "G",
                    "name": "楼层间广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 267,
                    "code": "H",
                    "name": "电梯广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 268,
                    "code": "I",
                    "name": "过道吊旗",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 269,
                    "code": "J",
                    "name": "安全门广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 270,
                    "code": "K",
                    "name": "播音广告",
                    "type": "publicAdType",
                    "childDataList": null
                },
                {
                    "id": 271,
                    "code": "L",
                    "name": "LED大屏广告",
                    "type": "publicAdType",
                    "childDataList": null
                }
            ]
        },
        {
            "fieldName": "brandSponsorType",
            "fieldDesc": "品牌赞助广告分类（多选）",
            "fieldFormat": "checkbox",
            "indicator": "publicResource",
            "relationData": null,
            "formId": 1,
            "orderId": 85,
            "optionList": [
                {
                    "id": 254,
                    "code": "A",
                    "name": "观光梯地板",
                    "type": "brandSponsorType",
                    "childDataList": null
                },
                {
                    "id": 255,
                    "code": "B",
                    "name": "公区地毯",
                    "type": "brandSponsorType",
                    "childDataList": null
                },
                {
                    "id": 256,
                    "code": "C",
                    "name": "扶梯温馨提醒",
                    "type": "brandSponsorType",
                    "childDataList": null
                },
                {
                    "id": 257,
                    "code": "D",
                    "name": "收银信封",
                    "type": "brandSponsorType",
                    "childDataList": null
                },
                {
                    "id": 258,
                    "code": "E",
                    "name": "商场商户排名公示",
                    "type": "brandSponsorType",
                    "childDataList": null
                },
                {
                    "id": 259,
                    "code": "Z",
                    "name": "其他（填写）",
                    "type": "brandSponsorType",
                    "childDataList": null
                }
            ]
        }
    ];

    $scope.field1 = {};
    $scope.surveySubmit = function(){
        $log.info("field1:"+$scope.field1);
    }

}
