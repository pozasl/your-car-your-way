// min length 8, at least 1 uppercase, 1 lowercase, 1 number and 1 special char.
export const passwordStrengthReg = /^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\D*\d).{8,}$/;