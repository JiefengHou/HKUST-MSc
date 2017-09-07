
# coding: utf-8

# In[4]:

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

    for index in range(len(list)):
        for key in range(index+1,len(list)):
            intersection = len(set.intersection(*[set(dict[list[index]]), set(dict[list[key]])]))
            union = len(set.union(*[set(dict[list[index]]), set(dict[list[key]])]))
            result=intersection/float(union)
            if result >= 0.5:
                print ("Match Document1 ID:{0}, Document2 ID:{1}, Similarity:{2}".format(list[index],list[key],result)) 


# In[ ]:



