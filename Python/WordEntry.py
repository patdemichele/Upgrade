class WordEntry():
	POSmap = ["NN","VB","JJ","MD","RB","DT","PRO",None,"PREP"]
	def __init__(self, name):
		self.pos= None
		self.name = name.lower()
		self.relnsCount = [0,0,0,0,0,0,0,0,0]
		self.nounList,self.verbList,self.adjList,self.modalList,self.advList,self.determinerList,self.pronounList,self.otherList,self.prepList={},{},{},{},{},{},{},{},{}
		self.relns = [self.nounList,self.verbList,self.adjList,self.modalList,self.advList,self.determinerList,self.pronounList,self.otherList,self.prepList]
		
	def __eq__(self,otherW):
		w = None
		if not isinstance(otherW,WordEntry):
			return False
		w=otherW
		if((self.name + self.pos).lower()==(w.name+w.pos).lower()):
			return True
		return False
	def __repr__(self):
		return self.name
	def hash(self):
		return hash(self.__repr__())
	def returnIndex(self):
		POSmap = ["NN","VB","JJ","MD","RB","DT","PRO","OTHER","PREP"]
		if self.pos in POSmap:
			return POSmap.index(self.pos)
		return 7
	#there is an overloaded method for just a string but I can't use it bc python
	def contains(self,o):
		# here we deal with overloaded methods. thankfully we take one argument and figure out what to do with it
		if isinstance(o,WordEntry):
			for i in range(10):
				for r in self.relns[i]:
					if r[2]==o:
						return True
			return False
		if isintance(o,tuple):
			for i in range(len(self.relns)):
				if o in relns[i]:
					return True
			return False
		return False
	def get(self,w):
		for i in range(9):
			for r in self.relns[i]:
				if r[2]==w:
					return relns[i][r]
		return 0
	
	def setPOS(self,pos):
		posDict = {"CC":"CC","IN":"PREP","PREP":"PREP","DT":"DT","EX":"EX","JJ":"JJ","JJR":"JJ","JJS":"JJ","MD":"MD","NN":"NN","NNS":"NN","NNP":"NN","NNPS":"NN","PDT":"DT","PRP":"PRO","PRP$":"PRO","RB":"RB","RBR":"RB","RBS":"RB","VB":"VB","VBD":"VB","VBG":"JJ","VBN":"JJ","VBP":"VB","VBZ":"VB","WDT":"DT","WP":"PRO","WP$":"PRO","WRB":"RB"}
		if pos in posDict:
			self.pos = posDict[pos]
		else:
			self.pos = "OTHER"



	
		
		
	

	
