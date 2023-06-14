import {PrismaClient} from "@prisma/client";

const prisma = new PrismaClient();

export const getProducts = async (req, res) =>{
    try {
        const response = await prisma.product.findMany();
        res.status(200).json(response);
    } catch (error) {
        res.status(500).json({msg: error.message});
    }
}

export const getProductById = async (req, res) =>{
    try {
        const response = await prisma.product.findUnique({
            where:{
                id: Number(req.params.id)
            }
        });
        res.status(200).json(response);
    } catch (error) {
        res.status(404).json({msg: error.message});
    }
}

export async function getPriceByLabel(req) {
    const { label } = req.params;
  
    const products = await prisma.product.findMany({
      where: {
        label: Number(req.params.label),
      }
    });
  
    if (products.length === 0) {
      throw new Error('No matching products found');
    }
  
    const priceRangeSet = new Set();

    for (const product of products) {
      const price = product.price;
  
      if (price === 0) {
        priceRangeSet.add('0');
      } else if (price >= 1 && price <= 15000) {
        priceRangeSet.add('1-15.000');
      } else if (price > 15000 && price <= 30000) {
        priceRangeSet.add('15.001-30.000');
      } else if (price > 30000 && price <= 50000) {
        priceRangeSet.add('30.001-50.000');
      } else {
        priceRangeSet.add('More than 50.000');
      }
    }
  
    const priceRange = Array.from(priceRangeSet);
  
    return priceRange;
}

export const getProductByLabel = async (req, res) =>{
    try {
        const priceRange = await getPriceByLabel(req);
        res.status(200).json({ msg: priceRange });
    }catch (error) {
        res.status(404).json({ msg: error.message });
    }
    
}

export const getLocationByLabel = async (req, res) =>{
    try {
        const response = await prisma.product.findMany({
            distinct: ['location'],
            orderBy: {
                location: "asc"
            },
            where:{
                label: Number(req.params.label)
            },
            select:{
                location: true
            }
        })
        
        res.status(200).json(response);
    } catch (error) {
        res.status(404).json({msg: error.message});
    }
}

export const createProduct = async (req, res) =>{
    const {name, price, image, email, description, location, rating, label} = req.body;
    try {
        const product = await prisma.product.create({
            data:{
                name: name,
                price: price,
                email: email,
                description: description,
                location: location,
                image: image,
                label: label,
            }
        });
        res.status(201).json(product);
    } catch (error) {
        res.status(400).json({msg: error.message});
    }
}


export const updateProduct = async (req, res) =>{
    const {name, price, image, description, location, email, rating, label} = req.body;
    try {
        const product = await prisma.product.update({
            where:{
                id: Number(req.params.id)
            },
            data:{
                name: name,
                price: price,
                email: email,
                description: description,
                location: location,
                image: image,
                label: label,
            }
        });
        res.status(200).json(product);
    } catch (error) {
        res.status(400).json({msg: error.message});
    }
}

export const deleteProduct = async (req, res) =>{
    try {
        const product = await prisma.product.delete({
            where:{
                id: Number(req.params.id)
            }
        });
        res.status(200).json(product);
    } catch (error) {
        res.status(400).json({msg: error.message});
    }
}