
export const formDataToJson = async (formData:Promise<FormData>) => {
    const data = await formData 
    return JSON.stringify(Object.fromEntries(data.entries()));
}