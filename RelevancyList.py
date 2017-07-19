#RelevancyListPython
#Authored by Jeremy Dohmann, transcribed/modified by Patrick DeMichele

#imports will go here when needed
import re
from WordEntry import *

import numpy
import unicodedata

def deAccent(str):
    strC = unicodedata.normalize('NFD', str)
    return strC # not complete
        
def clean(str):
    retval = ""
    s1 = str.split(" ")
    count = len(s1)
    iter1 = iter(s1)
    while count>0:
        token = ""
        s2 = iter(next(s1).split("!"))
        token+=next(s2)+"!"+next(s2)+"!"+replaceAll(next(s2),"[^a-zA-Z/]")+"!"+next(s2)
        retval+=token+" "
        count-=1
    return str
        
def reconstructList(fileName):
        bf = open(fileName,encoding = "latin-1")
        r = RelevancyList()
        k = 0
        for line in bf:
            if line == "":
                continue
                print("continued")
            k+=1
            if k % 1000 == 0:
                    print(k)
            j = line.split("|")
            st = iter(j)
            st_left = len(j)
            nameTokenSplit = iter(next(st).split("@"))
            st_left-=1
            tot = 0
            rootWordName = next(nameTokenSplit)
            if re.search('[^a-z0-9/]',rootWordName):
                continue
            #print(rootWordName)
            #if not re.search("([0-9]+)!([a-z_]+)!([a-z]+)/([a-z]+)/([A-Z]+)!([0-9]+)",rootWordName):
             #           continue
            if (r.contains(rootWordName)):
                rootWord = r.get(rootWordName)
            else:
                rootWord = WordEntry(rootWordName)
            wordpos1 = next(nameTokenSplit)
            #print("root POS :"+wordpos1)
            rootWord.setPOS(wordpos1)
            if st_left>0:
                ilen = next(st).strip().split(" ")
                itlen = len(ilen)
                relnsArray = iter(ilen)
                st_left-=1
                while itlen>0:
                    reln = next(relnsArray).strip()
                    itlen-=1
                    if not re.search("([0-9]+)!([a-z_]+)!([a-z]+)/([a-z]+)/([A-Z]+)!([0-9]+)",reln):
                        continue
                    components = iter(reln.split("!"))
                    index = int(replaceAll(next(components),"[^0-9]"))
                    type = next(components)
                    otherWordNameAndPOS = next(components)
                    otherWordInfo = otherWordNameAndPOS.split("/")
                    otherWordName = otherWordInfo[0]+"/"+otherWordInfo[1]
                    otherWordPOS = otherWordInfo[2]
                    #print("other word POS: "+otherWordPOS)
                    counter = next(components).strip()
                    count = int(counter)
                    tot += count
                    if not r.contains(otherWordName):
                        otherWord = WordEntry(otherWordName)
                        otherWord.setPOS(otherWordPOS)
                    else:
                        otherWord = r.get(otherWordName)
                    rootWord.relns[otherWord.returnIndex()][(rootWordName,type,otherWordName)] = rootWord.relns[otherWord.returnIndex()].get((rootWordName,type,otherWordName),0)+count
                    rootWord.relnsCount[otherWord.returnIndex()]+=count
                    otherWord.relns[rootWord.returnIndex()][(rootWordName,type,otherWordName)] = otherWord.relns[rootWord.returnIndex()].get((rootWordName,type,otherWordName),0)+count
                    otherWord.relnsCount[otherWord.returnIndex()]+=count                    
                    r.add(otherWord)
            if tot == 0:
                continue
            r.add(rootWord)
        bf.close()
        return r


def replaceAll(str, regex):
    retval = ""
    for s in str:
        if re.search(regex,s):
            retval+=""
        else:
            retval+=s
    return retval
                                                    
class RelevancyList():
    def __init__(self):
        self.nounList,self.verbList,self.adjList,self.modalList,self.advList,self.determinerList,self.pronounList,self.otherList,self.prepList={},{},{},{},{},{},{},{},{}
        self.totalList = [self.nounList,self.verbList,self.adjList,self.modalList,self.advList,self.determinerList,self.pronounList,self.otherList,self.prepList]
    
    def size(self):
        ret = 0
        for l in self.totalList:
            ret+=len(l)
        return ret
    def add(self,gov):
        POSmap = ["nn","vb","jj","md","rb","dt","pro",None,"prep"]
        i = POSmap.index(gov.pos) if gov.pos in POSmap else 7
        self.totalList[i][gov.name] = gov
    
    def PrintRelevancyList(self,fileName): #throws IO??
        #create 'Printwriter'
        pw = open(fileName,'w')
        for l in self.totalList:
            for k in l:
                w = l[k]
                pw.write(w.name+'@'+w.pos+'|')
                
                i = 0
                #we may need to try catch this
                for h in w.relns:
                    for r in h:
                        pw.write(" " + i + "!" + r.name +  "!" + r.otherWord.name + "/" + r.otherWord.pos + "!" + h[r])
                        i+=1
                pw.write("\n")
                
        pw.close()
    def contains(self,str):
        for h in self.totalList:
            if str in h:
                return True
        return False
    
    def get(self,str):
        for h in self.totalList:
            if str in h:
                return h[str]
        return None

                
    def getWithoutPOS(self,str):
        index,max = 0,0
        d = {}    
        str = str.lower()
        endings = ['/vb','/vbz','/vbd','/jj','/vbg','/vbn','/vbp','/nn','/nns','/nnp','/nnps','/jjr','/jjs','/in','/pdt','/pos','/prp','/prp$','/rb','/rbr','/rbs','/rp','/cc','/cd','/dt','/ex','/fw','/ls','/md','/sym','/to','/uh','/wdt','/wp','/wp$']
        for i in range(len(endings)):
            if self.contains(str+endings[i]):
                h = self.get(str+endings[i]).relnsCount
                sum = 0
                for element in h:
                    sum+=element
                d[i] = sum
            else:
                d[i] = 0
        for key in d:
            if d[key]>max:
                max = d[key]
                index = key
        return self.get(str+endings[index])

    def containsWithoutPOS(self,str):
        str = str.lower()
        endings = ['/vb','/vbz','/vbd','/jj','/vbg','/vbn','/vbp','/nn','/nns','/nnp','/nnps','/jjr','/jjs','/in','/pdt','/pos','/prp','/prp$','/rb','/rbr','/rbs','/rp','/cc','/cd','/dt','/ex','/fw','/ls','/md','/sym','/to','/uh','/wdt','/wp','/wp$']
        for end in endings:
            if self.contains(str+end):
                return True
        return False
    
