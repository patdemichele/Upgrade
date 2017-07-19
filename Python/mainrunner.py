from RelevancyList import *
from WordEntry import *
from patisagenius import *
from sklearn import svm
from sklearn import datasets
def runIt(r,open1,close,testNumber,train=svm.SVC()):
	trainLoc = "C:\\Users\\Patrick DeMichele\\Documents\\Upgrade\\ForPatrick\\WordWord\\Training\\training set pure.txt"
	testLoc = "C:\\Users\\Patrick DeMichele\\Documents\\Upgrade\\ForPatrick\\WordWord\\Test\\test set pure.txt"
	RLloc = "C:\\Users\\Patrick DeMichele\\Documents\\RL.txt"
	TSVloc = "C:\\Users\\Patrick DeMichele\\Documents\\smashingReport.txt"
	f1 = open(trainLoc,'r')
	f2 = open(testLoc,'r')
	#dataset training, dataset testing
	#train = svm.SVC() by default in constructor
	#test = svm.SVC()
	doubleList = []
	tagList = []
	#tsv = open(TSVloc,'w') [not important]


	initTag = 1
	count = 0
	realcount = 0
	for line in f1:
		count+=1
		
		if count<open1:
			continue
		if realcount>close:
			break
		st = iter(line.split(" "))
		s1 = next(st)
		s2 = next(st)
		tag = int(next(st))
		print(s1+" "+s2+" "+str(tag))
		w1 = r.get(s1)
		w2 = r.get(s2)
		if w1 != None and w2 != None and w1 != w2 and tag==initTag:
			print("effective")
			d = explicitProb(w1,w2)
			#training.add( new DenseInstance(d,tag) )
			doubleList.append(d)
			tagList.append(tag)
			for i in range(9):
				print(str(w1.relnsCount[i])+" "+str(w2.relnsCount[i]))
			print(str(count))
			realcount+=1
			initTag = initTag*(-1)
	doubleList,tagList = numpy.asarray(doubleList),numpy.asarray(tagList).reshape(-1,1)
	print(str(doubleList))
	print(str(tagList))
	train.fit(doubleList,tagList)
	realDouble = []
	realTag = []
	linecount = 0
	linemax = testNumber
	for line in f2:
		
		
		if linecount>linemax:
			break
		st = iter(line.split(" "))
		s1 = next(st)
		s2 = next(st)
	
		tag = int(next(st))
		w1 = r.get(s1)
		w2 = r.get(s2)
		if w1 != None and w2 != None and w1 != w2 and tag == initTag:
			d = explicitProb(w1,w2)
			realDouble.append(d)
			realTag.append(tag)
			print(str(linecount))
			linecount+=1
			initTag = initTag*(-1)

	f1.close()
	f2.close()
	p = []
	for i in range(len(realDouble)):
		p.append(train.predict(numpy.asarray(realDouble[i]))[0])	
	

	fp,tp,fn,tn=0,0,0,0
	for i in range(len(p)):
		op = p[i]
		r = realTag[i]
		if op == r and r == -1:
			tn+=1
		if op == r and r == 1:
			tp+=1
		if op != r and r == -1:
			fp+=1
		if op != r and r == 1:
			fn+=1
	print(" fp tp fn tn ")
	print(str(fp)+" "+str(tp)+" "+str(fn)+" "+str(tn))
	#print("accuracy = "+str((tp+tn)/(fp+tp+fn+tn)))
	return train