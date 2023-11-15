from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
import pandas as pd

def preprocessing(data):
    data = str(data)
    data = data.lower()
    return data

def initRecommender(data_tourism):
    tv = TfidfVectorizer(max_features=5000)
    
    data_tourism['Deskripsi'] = data_tourism['Deskripsi'] + ' ' + data_tourism['Label (str)']
    data_tourism.drop(['email', 'Label (int)', 'Label (str)'], axis=1, inplace=True)
    data_tourism.Deskripsi = data_tourism.Deskripsi.apply(preprocessing)

    vecs = tv.fit_transform(data_tourism.Deskripsi).toarray()
    similarity = cosine_similarity(vecs)

    return data_tourism, similarity

def contentBasedFiltering(nama_tempat, filterKota, data_tourism, similarity):
    nama_tempat_index = data_tourism[data_tourism['Wisata']==nama_tempat].index[0]
    distancess = similarity[nama_tempat_index]
    nama_tempat_list = sorted(list(enumerate(distancess)),key=lambda x: x[1],reverse=True)[1:10]
    
    recommended_nama_tempats = []
    for i in nama_tempat_list:
            nama = data_tourism.iloc[i[0]].Wisata
            kota = data_tourism.iloc[i[0]]['Kab/Kota']
            wisata_id = int(data_tourism.iloc[i[0]]['id'])

            if filterKota is None or kota == filterKota:
                recommended_nama_tempats.append({"id": wisata_id, "name": nama})
        
    return recommended_nama_tempats