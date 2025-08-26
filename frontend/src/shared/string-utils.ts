export const uppercaseFirstLetter = (x:string) => {
    if(x.length==0) return x 
    return x.charAt(0).toUpperCase() + x.substring(1)
}
