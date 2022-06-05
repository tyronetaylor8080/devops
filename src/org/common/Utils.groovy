package org.common


//格式化输出-->信息和颜色传参
def PrintMes(value,color){
    //定义字典
    colors = [
        "black"  : "\033[30m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m",
        "red"    : "\033[31m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m",
        "green"  : "\033[32m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m",
        "yellow" : "\033[33m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m",
        "blue"   : "\033[34m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m",
        "magenta": "\033[35m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m",
        "cyan"   : "\033[36m >>>>>>>>>>> ${value} <<<<<<<<<<< \033[0m"
    ]

    //调用
    ansiColor('xterm') {
        println(colors[color])
    }
}


