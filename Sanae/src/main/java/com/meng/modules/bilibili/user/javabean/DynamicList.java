package com.meng.modules.bilibili.user.javabean;
import java.util.ArrayList;
import java.util.List;

public class DynamicList {
//    {
//        "code": 0,
//        "msg": "",
//        "message": "",
//        "data": {
//            "has_more": 1,
//            "cards": [{
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 122862903,
//                    "acl": 0,
//                    "view": 219,
//                    "repost": 0,
//                    "comment": 0,
//                    "like": 3,
//                    "is_liked": 0,
//                    "dynamic_id": 512257678500918223,
//                    "timestamp": 1618107685,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "512257678500918223",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "122862903"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"距离复活还有-2分钟\",\"id\":122862903,\"is_fav\":0,\"pictures\":[{\"img_height\":1080,\"img_size\":1383.65,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/0114b35c1de4028d13307a2374bc3d1509cade34.jpg\",\"img_tags\":null,\"img_width\":2340}],\"pictures_count\":1,\"reply\":0,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1618107685},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0,\"verify\":{}},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 121887480,
//                    "acl": 0,
//                    "view": 221,
//                    "repost": 0,
//                    "comment": 1,
//                    "like": 8,
//                    "is_liked": 0,
//                    "dynamic_id": 509668474646953167,
//                    "timestamp": 1617504839,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "509668474646953167",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "121887480"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"分享图片\",\"id\":121887480,\"is_fav\":0,\"pictures\":[{\"img_height\":480,\"img_size\":397.58,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/c8a8438e4b7e495210c69fe44a3a0f5ac6a13f0d.gif\",\"img_tags\":null,\"img_width\":356}],\"pictures_count\":1,\"reply\":1,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1617504839},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 120372103,
//                    "acl": 0,
//                    "view": 153,
//                    "repost": 0,
//                    "comment": 1,
//                    "like": 4,
//                    "is_liked": 0,
//                    "dynamic_id": 506491525986547481,
//                    "timestamp": 1616765148,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "506491525986547481",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "120372103"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"迷城骇兔池子出品\",\"id\":120372103,\"is_fav\":0,\"pictures\":[{\"img_height\":1080,\"img_size\":787.6,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/929d21dbf8fef9b67de32bb9dcf9dade7c462a5f.jpg\",\"img_tags\":null,\"img_width\":2340}],\"pictures_count\":1,\"reply\":1,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1616765148},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0,\"verify\":{}},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 119427519,
//                    "acl": 0,
//                    "view": 191,
//                    "repost": 0,
//                    "comment": 1,
//                    "like": 4,
//                    "is_liked": 0,
//                    "dynamic_id": 503883282549333575,
//                    "timestamp": 1616157869,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "503883282549333575",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "119427519"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"偷渡\",\"id\":119427519,\"is_fav\":0,\"pictures\":[{\"img_height\":1080,\"img_size\":975.39,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/b5cb5117b335a8594eb8223578dec9f3f11febe6.jpg\",\"img_tags\":null,\"img_width\":2340},{\"img_height\":1080,\"img_size\":685.65,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/3559e04bbaf79a75da8c920ff7dc1f9bb674f6d9.jpg\",\"img_tags\":null,\"img_width\":2340}],\"pictures_count\":2,\"reply\":1,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1616157869},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0,\"verify\":{\"asw\":{\"fl\":15},\"sw\":{\"fl\":15}}},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 119366867,
//                    "acl": 0,
//                    "view": 156,
//                    "repost": 0,
//                    "comment": 0,
//                    "like": 3,
//                    "is_liked": 0,
//                    "dynamic_id": 503759746401036725,
//                    "timestamp": 1616129106,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "503759746401036725",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "119366867"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"微信迷惑行为\",\"id\":119366867,\"is_fav\":0,\"pictures\":[{\"img_height\":1759,\"img_size\":530.14,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/47c3e8cd3dc6178ca94c4823683221dc3eb54c78.jpg\",\"img_tags\":null,\"img_width\":1080}],\"pictures_count\":1,\"reply\":0,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1616129106},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0,\"verify\":{}},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 118773230,
//                    "acl": 0,
//                    "view": 150,
//                    "repost": 0,
//                    "comment": 0,
//                    "like": 5,
//                    "is_liked": 0,
//                    "dynamic_id": 501988252776418011,
//                    "timestamp": 1615716648,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "501988252776418011",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "118773230"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"不是换装游戏女主角 我信了.jpg\",\"id\":118773230,\"is_fav\":0,\"pictures\":[{\"img_height\":1080,\"img_size\":922.5599999999999,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/5b6e2cb9ad78ea18a81be0512dcd721cd656ce81.jpg\",\"img_tags\":null,\"img_width\":2340}],\"pictures_count\":1,\"reply\":0,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1615716648},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0,\"verify\":{}},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 8,
//                    "rid": 714593167,
//                    "acl": 0,
//                    "view": 285,
//                    "repost": 0,
//                    "like": 5,
//                    "is_liked": 0,
//                    "dynamic_id": 501631285158634473,
//                    "timestamp": 1615633535,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "501631285158634473",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "714593167",
//                    "bvid": "BV1DX4y1V7y4"
//                },
//                "card": "{\"aid\":714593167,\"attribute\":0,\"cid\":309789723,\"copyright\":1,\"ctime\":1615633535,\"desc\":\"菜 菜 菜\\n菜 我 菜\\n菜 菜 菜\",\"dimension\":{\"height\":590,\"rotate\":0,\"width\":1280},\"duration\":373,\"dynamic\":\"\",\"item\":{\"at_control\":\"\"},\"jump_url\":\"bilibili:\\/\\/video\\/714593167\\/?page=1&player_preload=null&player_width=1280&player_height=590&player_rotate=0\",\"owner\":{\"face\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"mid\":64483321,\"name\":\"妖怪之山的厄神\"},\"pic\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/archive\\/c82f947441a2b805b35d001bd5c5dd3001886d66.jpg\",\"player_info\":null,\"pubdate\":1615633535,\"rights\":{\"autoplay\":1,\"bp\":0,\"download\":0,\"elec\":0,\"hd5\":0,\"is_cooperation\":0,\"movie\":0,\"no_background\":0,\"no_reprint\":1,\"pay\":0,\"ugc_pay\":0,\"ugc_pay_preview\":0},\"short_link\":\"https:\\/\\/b23.tv\\/BV1DX4y1V7y4\",\"short_link_v2\":\"https:\\/\\/b23.tv\\/BV1DX4y1V7y4\",\"stat\":{\"aid\":714593167,\"coin\":2,\"danmaku\":0,\"dislike\":0,\"favorite\":0,\"his_rank\":0,\"like\":5,\"now_rank\":0,\"reply\":4,\"share\":0,\"view\":32},\"state\":0,\"tid\":17,\"title\":\"菜 我 菜\",\"tname\":\"单机游戏\",\"videos\":1}",
//                "extend_json": "{\"\":{\"ogv\":{\"ogv_id\":0}},\"dispute\":{\"content\":\"\"},\"from\":{\"from\":\"\"},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"},\"topic\":{\"is_attach_topic\":1}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "topic_info": {
//                        "topic_details": [{
//                            "topic_id": 18153558,
//                            "topic_name": "禁限超越",
//                            "is_activity": 1,
//                            "topic_link": "https://www.bilibili.com/blackboard/dynamic/57867"
//                        }, {
//                            "topic_id": 2695011,
//                            "topic_name": "崩坏三",
//                            "is_activity": 1,
//                            "topic_link": "https://www.bilibili.com/blackboard/dynamic/57263"
//                        }]
//                    },
//                    "usr_action_txt": "投稿了视频",
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    },
//                    "show_tip": {
//                        "del_tip": "要删除动态吗？"
//                    },
//                    "cover_play_icon_url": "https://i0.hdslb.com/bfs/album/2269afa7897830b397797ebe5f032b899b405c67.png"
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 117444013,
//                    "acl": 0,
//                    "view": 172,
//                    "repost": 0,
//                    "comment": 0,
//                    "like": 7,
//                    "is_liked": 0,
//                    "dynamic_id": 498667968177338290,
//                    "timestamp": 1614943584,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "498667968177338290",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "117444013"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"打不过 告辞.jpg\",\"id\":117444013,\"is_fav\":0,\"pictures\":[{\"img_height\":1080,\"img_size\":1348.54,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/106541b40d5b53488b11f0d7149e20f614161b03.jpg\",\"img_tags\":null,\"img_width\":2340}],\"pictures_count\":1,\"reply\":0,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1614943584},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0,\"verify\":{}},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 1,
//                    "rid": 498503595486189252,
//                    "acl": 0,
//                    "view": 147,
//                    "repost": 0,
//                    "comment": 0,
//                    "like": 2,
//                    "is_liked": 0,
//                    "dynamic_id": 498503595485108933,
//                    "timestamp": 1614905313,
//                    "pre_dy_id": 498292686828566927,
//                    "orig_dy_id": 498292686828566927,
//                    "orig_type": 8,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 0,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "498503595485108933",
//                    "pre_dy_id_str": "498292686828566927",
//                    "orig_dy_id_str": "498292686828566927",
//                    "rid_str": "498503595486189252",
//                    "origin": {
//                        "uid": 27534330,
//                        "type": 8,
//                        "rid": 374442590,
//                        "acl": 0,
//                        "view": 2028669,
//                        "repost": 1293,
//                        "like": 0,
//                        "dynamic_id": 498292686828566927,
//                        "timestamp": 1614856207,
//                        "pre_dy_id": 0,
//                        "orig_dy_id": 0,
//                        "uid_type": 1,
//                        "stype": 0,
//                        "r_type": 1,
//                        "inner_id": 0,
//                        "status": 1,
//                        "dynamic_id_str": "498292686828566927",
//                        "pre_dy_id_str": "0",
//                        "orig_dy_id_str": "0",
//                        "rid_str": "374442590",
//                        "bvid": "BV1wZ4y1P7nR"
//                    }
//                },
//                "card": "{ \"user\": { \"uid\": 64483321, \"uname\": \"妖怪之山的厄神\", \"face\": \"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\" }, \"item\": { \"rp_id\": 498503595486189252, \"uid\": 64483321, \"content\": \"分享动态\", \"ctrl\": \"\", \"orig_dy_id\": 498292686828566927, \"pre_dy_id\": 498292686828566927, \"timestamp\": 1614905313, \"reply\": 0, \"orig_type\": 8 }, \"origin\": \"{\\\"aid\\\":374442590,\\\"attribute\\\":0,\\\"cid\\\":306236633,\\\"copyright\\\":1,\\\"ctime\\\":1614856207,\\\"desc\\\":\\\"“怎么，你难道没见过会吃老虎的兔子吗？”\\\\n全新SP角色「迷城骇兔」即将于4.7版本与舰长相会，今天就请舰长跟随本视频，对这位新角色进行初步的探索吧！\\\",\\\"dimension\\\":{\\\"height\\\":1080,\\\"rotate\\\":0,\\\"width\\\":1920},\\\"duration\\\":304,\\\"dynamic\\\":\\\"#崩坏3# #崩坏3骇兔入侵# 全新SP角色「迷城骇兔」即将于4.7版本与舰长相会！\\\",\\\"item\\\":{\\\"at_control\\\":\\\"\\\"},\\\"jump_url\\\":\\\"bilibili:\\\\\\/\\\\\\/video\\\\\\/374442590\\\\\\/?page=1&player_preload=null&player_width=1920&player_height=1080&player_rotate=0\\\",\\\"owner\\\":{\\\"face\\\":\\\"https:\\\\\\/\\\\\\/i0.hdslb.com\\\\\\/bfs\\\\\\/face\\\\\\/df011f690a4bc728271843b07a812470d88c82bc.jpg\\\",\\\"mid\\\":27534330,\\\"name\\\":\\\"崩坏3第一偶像爱酱\\\"},\\\"pic\\\":\\\"https:\\\\\\/\\\\\\/i1.hdslb.com\\\\\\/bfs\\\\\\/archive\\\\\\/fa9814bab0fee421e75b1ab1363fcba069c5539d.jpg\\\",\\\"player_info\\\":null,\\\"pubdate\\\":1614856206,\\\"rights\\\":{\\\"autoplay\\\":1,\\\"bp\\\":0,\\\"download\\\":0,\\\"elec\\\":0,\\\"hd5\\\":1,\\\"is_cooperation\\\":0,\\\"movie\\\":0,\\\"no_background\\\":0,\\\"no_reprint\\\":1,\\\"pay\\\":0,\\\"ugc_pay\\\":0,\\\"ugc_pay_preview\\\":0},\\\"share_subtitle\\\":\\\"已观看171.0万次\\\",\\\"short_link\\\":\\\"https:\\\\\\/\\\\\\/b23.tv\\\\\\/BV1wZ4y1P7nR\\\",\\\"short_link_v2\\\":\\\"https:\\\\\\/\\\\\\/b23.tv\\\\\\/BV1wZ4y1P7nR\\\",\\\"stat\\\":{\\\"aid\\\":374442590,\\\"coin\\\":28418,\\\"danmaku\\\":10193,\\\"dislike\\\":0,\\\"favorite\\\":17224,\\\"his_rank\\\":24,\\\"like\\\":105219,\\\"now_rank\\\":0,\\\"reply\\\":12328,\\\"share\\\":35793,\\\"view\\\":1719180},\\\"state\\\":0,\\\"tid\\\":172,\\\"title\\\":\\\"《崩坏3》全新SP角色「迷城骇兔」角色预告\\\",\\\"tname\\\":\\\"手机游戏\\\",\\\"videos\\\":1}\", \"origin_extend_json\": \"{\\\"\\\":{\\\"game\\\":{\\\"game_id\\\":94,\\\"platform\\\":\\\"1,2\\\"},\\\"ogv\\\":{\\\"ogv_id\\\":0}},\\\"dispute\\\":{\\\"content\\\":\\\"\\\"},\\\"from\\\":{\\\"from\\\":\\\"\\\",\\\"verify\\\":{}},\\\"like_icon\\\":{\\\"action\\\":\\\"\\\",\\\"action_url\\\":\\\"\\\",\\\"end\\\":\\\"\\\",\\\"end_url\\\":\\\"\\\",\\\"start\\\":\\\"\\\",\\\"start_url\\\":\\\"\\\"},\\\"topic\\\":{\\\"is_attach_topic\\\":1}}\", \"origin_user\": { \"info\": { \"uid\": 27534330, \"uname\": \"崩坏3第一偶像爱酱\", \"face\": \"https:\\/\\/i0.hdslb.com\\/bfs\\/face\\/df011f690a4bc728271843b07a812470d88c82bc.jpg\" }, \"card\": { \"official_verify\": { \"type\": 1, \"desc\": \"崩坏3手游\" } }, \"vip\": { \"vipType\": 2, \"vipDueDate\": 1671638400000, \"vipStatus\": 1, \"themeType\": 0, \"label\": { \"path\": \"\", \"text\": \"年度大会员\", \"label_theme\": \"annual_vip\", \"text_color\": \"#FFFFFF\", \"bg_style\": 1, \"bg_color\": \"#FB7299\", \"border_color\": \"\" }, \"avatar_subscript\": 1, \"nickname_color\": \"#FB7299\", \"role\": 3, \"avatar_subscript_url\": \"https:\\/\\/i0.hdslb.com\\/bfs\\/vip\\/icon_Certification_big_member_22_3x.png\" }, \"pendant\": { \"pid\": 4036, \"name\": \"崩坏3·天穹流星\", \"image\": \"https:\\/\\/i0.hdslb.com\\/bfs\\/garb\\/item\\/5caf84631f46c32bf31e8fa871b5742a2c1ceb18.png\", \"expire\": 0, \"image_enhance\": \"https:\\/\\/i0.hdslb.com\\/bfs\\/garb\\/item\\/3594b30dfb6e9608d9eb6e67f47a2c90dd2bbf71.webp\", \"image_enhance_frame\": \"https:\\/\\/i0.hdslb.com\\/bfs\\/garb\\/item\\/d5afe4b28412a982672a7b0eb304b146a457bb5c.png\" }, \"rank\": \"10000\", \"sign\": \"全新4.7版本「骇兔入侵」开启，天才骇客布朗尼登场！\", \"level_info\": { \"current_level\": 6 } } }",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"up_close_comment\":0},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "origin": {
//                        "topic_info": {
//                            "topic_details": [{
//                                "topic_id": 1620256,
//                                "topic_name": "崩坏3",
//                                "is_activity": 0,
//                                "topic_link": ""
//                            }, {
//                                "topic_id": 18801015,
//                                "topic_name": "崩坏3骇兔入侵",
//                                "is_activity": 1,
//                                "topic_link": "https://www.bilibili.com/blackboard/dynamic/86954"
//                            }, {
//                                "topic_id": 18802987,
//                                "topic_name": "迷城骇兔",
//                                "is_activity": 1,
//                                "topic_link": "https://www.bilibili.com/blackboard/dynamic/86945"
//                            }, {
//                                "topic_id": 1187005,
//                                "topic_name": "布洛妮娅",
//                                "is_activity": 0,
//                                "topic_link": ""
//                            }, {
//                                "topic_id": 3007,
//                                "topic_name": "手机游戏",
//                                "is_activity": 0,
//                                "topic_link": ""
//                            }, {
//                                "topic_id": 2694798,
//                                "topic_name": "miHoYo",
//                                "is_activity": 1,
//                                "topic_link": "https://www.bilibili.com/blackboard/dynamic/57319"
//                            }]
//                        },
//                        "usr_action_txt": "投稿了视频",
//                        "relation": {
//                            "status": 1,
//                            "is_follow": 0,
//                            "is_followed": 0
//                        },
//                        "attach_card": {
//                            "type": "game",
//                            "head_text": "相关游戏",
//                            "cover_url": "https://i0.hdslb.com/bfs/game/bf1bbd3a24e1c61e3483cd707fce4a52466b0e60.png",
//                            "cover_type": 1,
//                            "title": "崩坏3",
//                            "desc_first": "动作/崩坏3/崩坏学园",
//                            "desc_second": "全新4.7版本「骇兔入侵」开启，天才骇客布朗尼登场！",
//                            "jump_url": "https://www.biligame.com/detail?id=94&sourceFrom=1005",
//                            "button": {
//                                "type": 1,
//                                "jump_style": {
//                                    "text": "进入"
//                                },
//                                "jump_url": "https://www.biligame.com/detail?id=94&sourceFrom=1005"
//                            },
//                            "oid_str": "94"
//                        },
//                        "add_on_card_info": [{
//                            "add_on_card_show_type": 2,
//                            "attach_card": {
//                                "type": "game",
//                                "head_text": "相关游戏",
//                                "cover_url": "https://i0.hdslb.com/bfs/game/bf1bbd3a24e1c61e3483cd707fce4a52466b0e60.png",
//                                "cover_type": 1,
//                                "title": "崩坏3",
//                                "desc_first": "动作/崩坏3/崩坏学园",
//                                "desc_second": "全新4.7版本「骇兔入侵」开启，天才骇客布朗尼登场！",
//                                "jump_url": "https://www.biligame.com/detail?id=94&sourceFrom=1005",
//                                "button": {
//                                    "type": 1,
//                                    "jump_style": {
//                                        "text": "进入"
//                                    },
//                                    "jump_url": "https://www.biligame.com/detail?id=94&sourceFrom=1005"
//                                },
//                                "oid_str": "94"
//                            }
//                        }],
//                        "show_tip": {
//                            "del_tip": "要删除动态吗？"
//                        },
//                        "cover_play_icon_url": "https://i0.hdslb.com/bfs/album/2269afa7897830b397797ebe5f032b899b405c67.png"
//                    },
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 116780146,
//                    "acl": 0,
//                    "view": 195,
//                    "repost": 0,
//                    "comment": 0,
//                    "like": 12,
//                    "is_liked": 0,
//                    "dynamic_id": 496832174604606703,
//                    "timestamp": 1614516155,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "496832174604606703",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "116780146"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"分享图片\",\"id\":116780146,\"is_fav\":0,\"pictures\":[{\"img_height\":400,\"img_size\":83.84999999999999,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/b0794d15969f480025832e3de881da253434ea3f.jpg\",\"img_tags\":null,\"img_width\":512}],\"pictures_count\":1,\"reply\":0,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1614516155},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 115167476,
//                    "acl": 0,
//                    "view": 216,
//                    "repost": 0,
//                    "comment": 4,
//                    "like": 8,
//                    "is_liked": 0,
//                    "dynamic_id": 493506594308457377,
//                    "timestamp": 1613741858,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "493506594308457377",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "115167476"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"别天天c++了 换一个吧\",\"id\":115167476,\"is_fav\":0,\"pictures\":[{\"img_height\":2340,\"img_size\":999.5599999999999,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/6a1758380b0952e3194094fb69f8674bf9fbcc64.jpg\",\"img_tags\":null,\"img_width\":1080}],\"pictures_count\":1,\"reply\":4,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1613741858},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }, {
//                "desc": {
//                    "uid": 64483321,
//                    "type": 2,
//                    "rid": 115133159,
//                    "acl": 0,
//                    "view": 170,
//                    "repost": 0,
//                    "comment": 2,
//                    "like": 3,
//                    "is_liked": 0,
//                    "dynamic_id": 493468227374727586,
//                    "timestamp": 1613732925,
//                    "pre_dy_id": 0,
//                    "orig_dy_id": 0,
//                    "orig_type": 0,
//                    "user_profile": {
//                        "info": {
//                            "uid": 64483321,
//                            "uname": "妖怪之山的厄神",
//                            "face": "https://i1.hdslb.com/bfs/face/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg"
//                        },
//                        "card": {
//                            "official_verify": {
//                                "type": -1,
//                                "desc": ""
//                            }
//                        },
//                        "vip": {
//                            "vipType": 0,
//                            "vipDueDate": 0,
//                            "vipStatus": 0,
//                            "themeType": 0,
//                            "label": {
//                                "path": "",
//                                "text": "",
//                                "label_theme": "",
//                                "text_color": "",
//                                "bg_style": 0,
//                                "bg_color": "",
//                                "border_color": ""
//                            },
//                            "avatar_subscript": 0,
//                            "nickname_color": "",
//                            "role": 0,
//                            "avatar_subscript_url": ""
//                        },
//                        "pendant": {
//                            "pid": 0,
//                            "name": "",
//                            "image": "",
//                            "expire": 0,
//                            "image_enhance": "",
//                            "image_enhance_frame": ""
//                        },
//                        "rank": "10000",
//                        "sign": "此生无悔入东方 来世愿生幻想乡",
//                        "level_info": {
//                            "current_level": 5
//                        }
//                    },
//                    "uid_type": 1,
//                    "stype": 0,
//                    "r_type": 1,
//                    "inner_id": 0,
//                    "status": 1,
//                    "dynamic_id_str": "493468227374727586",
//                    "pre_dy_id_str": "0",
//                    "orig_dy_id_str": "0",
//                    "rid_str": "115133159"
//                },
//                "card": "{\"item\":{\"at_control\":\"\",\"category\":\"daily\",\"description\":\"开  心  一  刻\",\"id\":115133159,\"is_fav\":0,\"pictures\":[{\"img_height\":1080,\"img_size\":733.35,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/fede0a1957a399887825b6c6ee7c1a129d6ed94a.jpg\",\"img_tags\":null,\"img_width\":2340},{\"img_height\":1080,\"img_size\":787.21,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/0676be496ad375668e6221345f1a8f41a37a1bc9.jpg\",\"img_tags\":null,\"img_width\":2340},{\"img_height\":1080,\"img_size\":798.74,\"img_src\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/album\\/60781e453c7e50368cc62c2d4c5651b4b3114e62.jpg\",\"img_tags\":null,\"img_width\":2340}],\"pictures_count\":3,\"reply\":2,\"role\":[],\"settings\":{\"copy_forbidden\":\"0\"},\"source\":[],\"title\":\"\",\"upload_time\":1613732925},\"user\":{\"head_url\":\"https:\\/\\/i1.hdslb.com\\/bfs\\/face\\/74e9f1cbb3cd890b197562f6361a84f6b1271ed7.jpg\",\"name\":\"妖怪之山的厄神\",\"uid\":64483321,\"vip\":{\"avatar_subscript\":0,\"due_date\":0,\"label\":{\"label_theme\":\"\",\"path\":\"\",\"text\":\"\"},\"nickname_color\":\"\",\"status\":0,\"theme_type\":0,\"type\":0,\"vip_pay_type\":0}}}",
//                "extend_json": "{\"from\":{\"emoji_type\":1,\"from\":\"\",\"up_close_comment\":0},\"like_icon\":{\"action\":\"\",\"action_url\":\"\",\"end\":\"\",\"end_url\":\"\",\"start\":\"\",\"start_url\":\"\"}}",
//                "extra": {
//                    "is_space_top": 0
//                },
//                "display": {
//                    "relation": {
//                        "status": 1,
//                        "is_follow": 0,
//                        "is_followed": 0
//                    }
//                }
//            }],
//            "next_offset": 493468227374727586,
//            "_gt_": 0
//        }
//    }
    public int code;
    public String message;
    public int ttl;
    public Data data;

    public class Data {
        public ArrayList<Card> cards;

        public class Card {
            public Desc desc;
            public String card;

            public class Desc {
                public long dynamic_id;
                public long timestamp;
            }
        }
    }

    public  class CardTextWithImg {

        public Item item;
        public User user;

        public class Item {
            public String at_control;
            public String category;
            public String description;
            public int id;
            public int is_fav;
            public List<Pictures> pictures ;
            public int pictures_count;
            public int reply;
            public List<Object> role ;
            public Settings settings;
            public List<Object> source ;
            public String title;
            public int upload_time;

            public class Pictures {
                public int img_height;
                public double img_size;
                public String img_src;
                public String img_tags;
                public int img_width;
            }

            public class Settings {
                public String copy_forbidden;
            }
        }

        public class User {
            public String head_url;
            public String name;
            public int uid;
            public Vip vip;

            public class Vip {
                public int avatar_subscript;
                public int due_date;
                public Label label;
                public String nickname_color;
                public int status;
                public int theme_type;
                public int type;
                public int vip_pay_type;

                public class Label {
                    public String label_theme;
                    public String path;
                    public String text;
                }
            }
        }   
    }
}

