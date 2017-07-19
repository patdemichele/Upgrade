from random import *
import random
import math
from math import *
from WordEntry import *
from RelevancyList import *
def patIsAGenius(w1,w2):
	retval = []
	for i in range(9):
		if w1.relnsCount[i]<w2.relnsCount[i]:
			greater = w2
			lesser = w1
		else:
			greater = w1
			lesser = w2
		totalcount = 0
		mag = 0
		for R in lesser.relns[i]:
			if (greater.name,R[1],R[2]) in greater.relns[i]:
				mag+=lesser.relns[i][R]*greater.relns[i][(greater.name,R[1],R[2])]		
		if lesser.relnsCount[i]*lesser.relnsCount[i] == 0:
			retval.append(0.0)
		else:
			retval.append((mag)/(lesser.relnsCount[i]*lesser.relnsCount[i]))
	return retval
def patSillyMistake(w1,w2):
	retval = []
	mag1,mag2 = 0,0
	for i in range(9):
		mag1+=w1.relnsCount[i]*w1.relnsCount[i]
		mag2+=w1.relnsCount[i]*w2.relnsCount[i]
	mag1,mag2 = math.sqrt(mag1),math.sqrt(mag2)
	for i in range(9):
		retval.append(w1.relnsCount[i]*w2.relnsCount[i]/mag1/mag2)
	return retval
def superPat(w1,w2):
	def dot(v1,v2):
		if len(v1)!=len(v2):
			return None
		counter = 0
		for i in range(len(v1)):
			counter+=v1[i]*v2[i]
		return counter
	retval = []
	vector1 = w1.relnsCount
	vector2 = w1.relnsCount
	retval.append(dot(vector1,vector2)/math.sqrt(dot(vector1,vector1))/math.sqrt(dot(vector2,vector2)))
	return retval
def patIsAGenius2(w1,w2,r):
	retval = []
	totalreln1,totalreln2 = 0,0
	for i in range(9):
		totalreln1+=w1.relnsCount[i]
		totalreln2+=w2.relnsCount[i]
	if totalreln1 > totalreln2:
		greater = w1
		lesser = w2
	else:
		greater = w2
		lesser = w1
	skip = 10000/(totalreln1*totalreln2)
	for i in range(9):
		totalcount = 0
		mag = 0
		for R in lesser.relns[i]:
			if (greater.name,R[1],R[2]) in greater.relns[i]:
				mag1=lesser.relns[i][R]*greater.relns[i][(greater.name,R[1],R[2])]
				mag+=mag1/r.get(R[2]).relnsCount[i]		
		if lesser.relnsCount[i]*lesser.relnsCount[i] == 0:
			retval.append(0.0)
		else:
			retval.append((mag)/(lesser.relnsCount[i]*lesser.relnsCount[i]))
	return retval
def explicitProb(w1,w2):
	retval = []
	seen = {}
	for i in range(9):
		for KeyR in w1.relns[i]:
			seen[KeyR] = seen.get(KeyR,0)+w1.relns[i][KeyR]
		for KeyR2 in w2.relns[i]:
			seen[KeyR2] = seen.get(KeyR2,0)+w2.relns[i][KeyR2]
		totalcount = 0
		overlap = 0
		for newkey in seen:
			if newkey in w1.relns[i] and newkey in w2.relns[i]:
				overlap+=seen[newkey]
			totalcount+=seen[newkey]
		retval.append(overlap/totalcount)
	print(str(retval))
	return retval
			
		
			
		