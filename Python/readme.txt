how to run the program

>>> import RelevancyList
>>> from RelevancyList import *
>>> r = reconstructList("relevancy list back-up.txt")
[wait a very long time for the relevancylist to load]
>>> import mainrunner
>>> from mainrunner import *
>>> runIt(r,[starting line of training set],[number of pairs of words you want to train],[number of words you want to test])
You can add an already-trained classifier as the fourth argument of this function. This function also has a return value of the classifier that it created.
If you need to change anything and run again, do the following.
>>>import imp
>>>imp.reload([name of module you changed])
it should display:
<module [name of module] [other info]>

Change the ending of any file from .py to .txt to view the file. It should be fairly easy to understand how the program works from there.

RelevancyList is your original RelevancyList Java class, transcribed to python. I replaced totalRelns with an array that displays the number of relns in each part of speech instead

WordEntry is WordEntry, with the corresponding totalRelns adjustment. I also got rid of the Relation class entirely. Now every "relation" key in the relation hashmaps is a 3-tuple (baseword.name, name, otherWord.name) -- note that this is a 3-tuple of strings, not wordentrys

patisagenius module has the methods I use to train. The name is slightly in jest, so don't take it too seriously. Right now, whatever I was last training with was screwing up, giving me 50% accuracy sometimes and all 0's for each vector other times. I'm still fixing this, and now that I have the RelevancyList accessible, trying out other things. I only sent you this zip file when I did because I was going to bed at that time, and will be busy the next day (July 4th)
readme.txt is just this
"relevancy list back-up.txt" is the RelevancyList in this local directory. That specific name is not hardcoded anywhere in the modules so it won't matter if you want to add another list or move it somewhere else.

this wasn't meant to be a comprehensive explanation. I recognize that you're an autodidact, but you will still probably have an easier time if you ask me what something does vs. figuring it out yourself. there were many small adjustments I made along the way