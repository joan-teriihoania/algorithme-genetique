#!/usr/bin/env python3
import fileinput
import sys
import re

filename = sys.argv[1]
replacements = [
    ["\includegraphics{", "\includegraphics[width=.6\\textwidth,center]{"],
    ["\href{", "\displayLinkIcon\href{"]
]

with open(filename, errors='ignore') as f:
    filecontent = f.readlines()

f = open(filename,"w+")
f.write("")
f.close()

f = open(filename,"a+")
for line in filecontent:
    line = line.replace("\\", "__BACK_SLASH__")
    line_to_print = line
    
    for replacement in replacements:
        replacement[0] = replacement[0].replace("\\", "__BACK_SLASH__")
        replacement[1] = replacement[1].replace("\\", "__BACK_SLASH__")
        if(replacement[0] in line_to_print): line_to_print = line.replace(replacement[0],replacement[1])
    
    line_to_print = line_to_print.replace("__BACK_SLASH__", "\\")
    f.write(line_to_print)

f.close()