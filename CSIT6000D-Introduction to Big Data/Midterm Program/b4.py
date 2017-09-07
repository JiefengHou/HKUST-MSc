
# coding: utf-8

# In[1]:

listoflist=[]         
with open('/Users/jiefenghou/Documents/bbcsport/bbcsport.mtx') as f:
    next(f)
    next(f)
    for i in f:
        list=[]
        i=i.strip('\n')
        list.append(i.split(' ')[0])
        list.append(i.split(' ')[1])
        list.append(i.split(' ')[2])
        listoflist.append(list)
    

    dict={}
    for list in listoflist:
        if not int(list[1]) in dict:
            dict[int(list[1])] = [int(list[0])]
        else:
            dict[int(list[1])].append(int(list[0]))

    list=[]
    for key in sorted(dict.keys()):
        list.append(key)
    
    
    print("B1:")
    b1List=[]
    for index in range(len(list)):
        for key in range(index+1,len(list)):
            intersection = len(set.intersection(*[set(dict[list[index]]), set(dict[list[key]])]))
            union = len(set.union(*[set(dict[list[index]]), set(dict[list[key]])]))
            result=intersection/float(union)
            if result >= 0.5:
                keyList=[]
                keyList.append(list[index])
                keyList.append(list[key])
                b1List.append(keyList)
                print ("B1: Match Document1 ID:{0}, Document2 ID:{1}, Similarity:{2}".format(list[index],list[key],result))
                
    #print (b1List)            

    print("==============================")
    print("B2:")
    #r=4613
    r= 4613
    # c is prime number which is slightly bigger than the r
    c= 4621
    #number of hash function
    num = 100
    print ("r = {0}, c = {1}, and number = {2}".format(r,c,num))
    
    import random
    randomList=[]
    count = num
    while count > 0:
        randomIndex = random.randint(0,r)           
        if randomIndex in randomList:
            continue
        randomList.append(randomIndex)
        count = count-1
    a=randomList

    randomList=[]
    count = num
    while count > 0:
        randomIndex = random.randint(0,r)           
        if randomIndex in randomList:
            continue
        randomList.append(randomIndex)
        count = count-1
    b=randomList
   
    results=[]
    for key in sorted(dict.keys()):
        result=[]
        for i in range(0,num):   
            miniHash = c+1
            for value in dict[key]:
                hash = (a[i]*value+b[i])%c
                if hash < miniHash:
                    miniHash = hash
            result.append(miniHash)
        results.append(result)
   
    list=dict.keys()
    b2List=[]
    for index in range(len(list)):
        result1 = results[index]
        for key in range(index+1,len(list)):
            result2 = results[key]
            count=0
            for k in range(0,num):
                if(result1[k] == result2[k]):
                    count=count+1
            score = count/float(num)
            if(score>=0.5):
                keyList=[]
                keyList.append(index+1)
                keyList.append(key+1)
                b2List.append(keyList)
                print ("B2: Match Document1 ID:{0}, Document2 ID:{1}, Similarity:{2}".format(index+1,key+1,score))
                
    #print (b2List)
    
    fp=0
    fn=0
    print ("false positive:")
    for i in b2List:
        notIn = 1
        for j in b1List:
            if i==j:
                notIn=0
                break
        if notIn == 1:
            print (i)
            fp=fp+1
        
    print ("false negative:")
    for i in b1List:
        notIn = 1
        for j in b2List:
            if i==j:
                notIn=0
                break
        if notIn == 1:
            print (i)
            fn=fn+1
            
            
    print ("false positive number is {0} and false negative number is {1}".format(fp,fn))


# In[ ]:



