
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
                    
    #r=4613
    r= 4613
    # c is prime number which is slightly bigger than the r
    c= 4621
    #number of hash function
    num = 50
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
                print ("B2: Match Document1 ID:{0}, Document2 ID:{1}, Similarity:{2}".format(index+1,key+1,score))


# In[ ]:



