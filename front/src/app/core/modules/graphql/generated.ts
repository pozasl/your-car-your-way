import { gql } from 'apollo-angular';
import { Injectable } from '@angular/core';
import * as Apollo from 'apollo-angular';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  /** The CountryCode scalar type as defined by ISO 3166-1 alpha-2. */
  CountryCode: { input: any; output: any; }
  /** An RFC-3339 compliant Full Date Scalar */
  Date: { input: any; output: any; }
  /** A slightly refined version of RFC-3339 compliant DateTime Scalar */
  DateTime: { input: any; output: any; }
  _Any: { input: any; output: any; }
  _FieldSet: { input: any; output: any; }
};

export type Account = {
  __typename?: 'Account';
  address?: Maybe<Address>;
  birthDate: Scalars['Date']['output'];
  createdAt?: Maybe<Scalars['DateTime']['output']>;
  email: Scalars['String']['output'];
  firstName: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  lastName: Scalars['String']['output'];
  role: Role;
  title: Title;
  updatedAt?: Maybe<Scalars['DateTime']['output']>;
};

export type AccountCredentials = {
  email: Scalars['String']['input'];
  password: Scalars['String']['input'];
};

export type Address = {
  __typename?: 'Address';
  city: Scalars['String']['output'];
  complement: Scalars['String']['output'];
  countryCode: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  postalCode: Scalars['String']['output'];
  region?: Maybe<Scalars['String']['output']>;
  street: Scalars['String']['output'];
};

export type Agency = {
  __typename?: 'Agency';
  address: Address;
  id: Scalars['ID']['output'];
  name: Scalars['String']['output'];
  vehicules?: Maybe<Array<Maybe<Vehicule>>>;
};

export type CustomerAccountInput = {
  address: NewAddressInput;
  birthDate: Scalars['Date']['input'];
  email: Scalars['String']['input'];
  firstName: Scalars['String']['input'];
  id: Scalars['ID']['input'];
  lastName: Scalars['String']['input'];
  password: Scalars['String']['input'];
  role: Role;
  title: Title;
};

export enum ErrorDetail {
  /**
   * The deadline expired before the operation could complete.
   *
   * For operations that change the state of the system, this error
   * may be returned even if the operation has completed successfully.
   * For example, a successful response from a server could have been
   * delayed long enough for the deadline to expire.
   *
   * HTTP Mapping: 504 Gateway Timeout
   * Error Type: UNAVAILABLE
   */
  DeadlineExceeded = 'DEADLINE_EXCEEDED',
  /**
   * The server detected that the client is exhibiting a behavior that
   * might be generating excessive load.
   *
   * HTTP Mapping: 429 Too Many Requests or 420 Enhance Your Calm
   * Error Type: UNAVAILABLE
   */
  EnhanceYourCalm = 'ENHANCE_YOUR_CALM',
  /**
   * The requested field is not found in the schema.
   *
   * This differs from `NOT_FOUND` in that `NOT_FOUND` should be used when a
   * query is valid, but is unable to return a result (if, for example, a
   * specific video id doesn't exist). `FIELD_NOT_FOUND` is intended to be
   * returned by the server to signify that the requested field is not known to exist.
   * This may be returned in lieu of failing the entire query.
   * See also `PERMISSION_DENIED` for cases where the
   * requested field is invalid only for the given user or class of users.
   *
   * HTTP Mapping: 404 Not Found
   * Error Type: BAD_REQUEST
   */
  FieldNotFound = 'FIELD_NOT_FOUND',
  /**
   * The client specified an invalid argument.
   *
   * Note that this differs from `FAILED_PRECONDITION`.
   * `INVALID_ARGUMENT` indicates arguments that are problematic
   * regardless of the state of the system (e.g., a malformed file name).
   *
   * HTTP Mapping: 400 Bad Request
   * Error Type: BAD_REQUEST
   */
  InvalidArgument = 'INVALID_ARGUMENT',
  /**
   * The provided cursor is not valid.
   *
   * The most common usage for this error is when a client is paginating
   * through a list that uses stateful cursors. In that case, the provided
   * cursor may be expired.
   *
   * HTTP Mapping: 404 Not Found
   * Error Type: NOT_FOUND
   */
  InvalidCursor = 'INVALID_CURSOR',
  /**
   * Unable to perform operation because a required resource is missing.
   *
   * Example: Client is attempting to refresh a list, but the specified
   * list is expired. This requires an action by the client to get a new list.
   *
   * If the user is simply trying GET a resource that is not found,
   * use the NOT_FOUND error type. FAILED_PRECONDITION.MISSING_RESOURCE
   * is to be used particularly when the user is performing an operation
   * that requires a particular resource to exist.
   *
   * HTTP Mapping: 400 Bad Request or 500 Internal Server Error
   * Error Type: FAILED_PRECONDITION
   */
  MissingResource = 'MISSING_RESOURCE',
  /**
   * Service Error.
   *
   * There is a problem with an upstream service.
   *
   * This may be returned if a gateway receives an unknown error from a service
   * or if a service is unreachable.
   * If a request times out which waiting on a response from a service,
   * `DEADLINE_EXCEEDED` may be returned instead.
   * If a service returns a more specific error Type, the specific error Type may
   * be returned instead.
   *
   * HTTP Mapping: 502 Bad Gateway
   * Error Type: UNAVAILABLE
   */
  ServiceError = 'SERVICE_ERROR',
  /**
   * Request failed due to network errors.
   *
   * HTTP Mapping: 503 Unavailable
   * Error Type: UNAVAILABLE
   */
  TcpFailure = 'TCP_FAILURE',
  /**
   * Request throttled based on server concurrency limits.
   *
   * HTTP Mapping: 503 Unavailable
   * Error Type: UNAVAILABLE
   */
  ThrottledConcurrency = 'THROTTLED_CONCURRENCY',
  /**
   * Request throttled based on server CPU limits
   *
   * HTTP Mapping: 503 Unavailable.
   * Error Type: UNAVAILABLE
   */
  ThrottledCpu = 'THROTTLED_CPU',
  /**
   * The operation is not implemented or is not currently supported/enabled.
   *
   * HTTP Mapping: 501 Not Implemented
   * Error Type: BAD_REQUEST
   */
  Unimplemented = 'UNIMPLEMENTED',
  /**
   * Unknown error.
   *
   * This error should only be returned when no other error detail applies.
   * If a client sees an unknown errorDetail, it will be interpreted as UNKNOWN.
   *
   * HTTP Mapping: 500 Internal Server Error
   */
  Unknown = 'UNKNOWN'
}

export enum ErrorType {
  /**
   * Bad Request.
   *
   * There is a problem with the request.
   * Retrying the same request is not likely to succeed.
   * An example would be a query or argument that cannot be deserialized.
   *
   * HTTP Mapping: 400 Bad Request
   */
  BadRequest = 'BAD_REQUEST',
  /**
   * The operation was rejected because the system is not in a state
   * required for the operation's execution.  For example, the directory
   * to be deleted is non-empty, an rmdir operation is applied to
   * a non-directory, etc.
   *
   * Service implementers can use the following guidelines to decide
   * between `FAILED_PRECONDITION` and `UNAVAILABLE`:
   *
   * - Use `UNAVAILABLE` if the client can retry just the failing call.
   * - Use `FAILED_PRECONDITION` if the client should not retry until
   * the system state has been explicitly fixed.  E.g., if an "rmdir"
   *      fails because the directory is non-empty, `FAILED_PRECONDITION`
   * should be returned since the client should not retry unless
   * the files are deleted from the directory.
   *
   * HTTP Mapping: 400 Bad Request or 500 Internal Server Error
   */
  FailedPrecondition = 'FAILED_PRECONDITION',
  /**
   * Internal error.
   *
   * An unexpected internal error was encountered. This means that some
   * invariants expected by the underlying system have been broken.
   * This error code is reserved for serious errors.
   *
   * HTTP Mapping: 500 Internal Server Error
   */
  Internal = 'INTERNAL',
  /**
   * The requested entity was not found.
   *
   * This could apply to a resource that has never existed (e.g. bad resource id),
   * or a resource that no longer exists (e.g. cache expired.)
   *
   * Note to server developers: if a request is denied for an entire class
   * of users, such as gradual feature rollout or undocumented allowlist,
   * `NOT_FOUND` may be used. If a request is denied for some users within
   * a class of users, such as user-based access control, `PERMISSION_DENIED`
   * must be used.
   *
   * HTTP Mapping: 404 Not Found
   */
  NotFound = 'NOT_FOUND',
  /**
   * The caller does not have permission to execute the specified
   * operation.
   *
   * `PERMISSION_DENIED` must not be used for rejections
   * caused by exhausting some resource or quota.
   * `PERMISSION_DENIED` must not be used if the caller
   * cannot be identified (use `UNAUTHENTICATED`
   * instead for those errors).
   *
   * This error Type does not imply the
   * request is valid or the requested entity exists or satisfies
   * other pre-conditions.
   *
   * HTTP Mapping: 403 Forbidden
   */
  PermissionDenied = 'PERMISSION_DENIED',
  /**
   * The request does not have valid authentication credentials.
   *
   * This is intended to be returned only for routes that require
   * authentication.
   *
   * HTTP Mapping: 401 Unauthorized
   */
  Unauthenticated = 'UNAUTHENTICATED',
  /**
   * Currently Unavailable.
   *
   * The service is currently unavailable.  This is most likely a
   * transient condition, which can be corrected by retrying with
   * a backoff.
   *
   * HTTP Mapping: 503 Unavailable
   */
  Unavailable = 'UNAVAILABLE',
  /**
   * Unknown error.
   *
   * For example, this error may be returned when
   * an error code received from another address space belongs to
   * an error space that is not known in this address space.  Also
   * errors raised by APIs that do not return enough error information
   * may be converted to this error.
   *
   * If a client sees an unknown errorType, it will be interpreted as UNKNOWN.
   * Unknown errors MUST NOT trigger any special behavior. These MAY be treated
   * by an implementation as being equivalent to INTERNAL.
   *
   * When possible, a more specific error should be provided.
   *
   * HTTP Mapping: 520 Unknown Error
   */
  Unknown = 'UNKNOWN'
}

export type LiveMessage = {
  __typename?: 'LiveMessage';
  at?: Maybe<Scalars['DateTime']['output']>;
  content: Scalars['String']['output'];
  from: Account;
  to: Account;
};

export type LiveMessageInput = {
  content: Scalars['String']['input'];
  fromUserId: Scalars['ID']['input'];
  toUserId: Scalars['ID']['input'];
};

export type Mutation = {
  __typename?: 'Mutation';
  registerCustomer?: Maybe<OperationResult>;
  /**
   *  updateAccount(account: CustomerAccountInput!): Void
   *  deleteAccount(account: CustomerAccountInput!): Void
   */
  sendLiveMessage?: Maybe<LiveMessage>;
};


export type MutationRegisterCustomerArgs = {
  account: NewCustomerAccountInput;
};


export type MutationSendLiveMessageArgs = {
  message: LiveMessageInput;
};

export type NewAccounInput = {
  birthDate: Scalars['Date']['input'];
  email: Scalars['String']['input'];
  firstName: Scalars['String']['input'];
  lastName: Scalars['String']['input'];
  password: Scalars['String']['input'];
  title: Title;
};

export type NewAddressInput = {
  city: Scalars['String']['input'];
  complement: Scalars['String']['input'];
  countryCode: Scalars['String']['input'];
  postalCode: Scalars['String']['input'];
  region?: InputMaybe<Scalars['String']['input']>;
  street: Scalars['String']['input'];
};

export type NewCustomerAccountInput = {
  address: NewAddressInput;
  birthDate: Scalars['Date']['input'];
  email: Scalars['String']['input'];
  firstName: Scalars['String']['input'];
  lastName: Scalars['String']['input'];
  password: Scalars['String']['input'];
  title: Title;
};

export type OperationResult = {
  __typename?: 'OperationResult';
  message: Scalars['String']['output'];
};

export type Query = {
  __typename?: 'Query';
  _entities: Array<Maybe<_Entity>>;
  _service: _Service;
  account?: Maybe<Account>;
  accounts?: Maybe<Array<Account>>;
  me?: Maybe<Account>;
  token?: Maybe<Scalars['String']['output']>;
};


export type Query_EntitiesArgs = {
  representations: Array<Scalars['_Any']['input']>;
};


export type QueryAccountArgs = {
  id: Scalars['ID']['input'];
};


export type QueryTokenArgs = {
  credentials: AccountCredentials;
};

export enum Role {
  Customer = 'CUSTOMER',
  CustomerService = 'CUSTOMER_SERVICE'
}

export type Subscription = {
  __typename?: 'Subscription';
  newLiveMessage?: Maybe<LiveMessage>;
};


export type SubscriptionNewLiveMessageArgs = {
  since?: InputMaybe<Scalars['DateTime']['input']>;
  toUserId: Scalars['ID']['input'];
};

export enum Title {
  Miss = 'MISS',
  Mr = 'MR',
  Mrs = 'MRS'
}

export type Vehicule = {
  __typename?: 'Vehicule';
  available: Scalars['Boolean']['output'];
  category: VehiculeCategory;
  registrationNumber: Scalars['String']['output'];
};

export type VehiculeCategory = {
  __typename?: 'VehiculeCategory';
  name: Scalars['String']['output'];
  ref?: Maybe<VehiculeCategoryRef>;
};

export enum VehiculeCategoryRef {
  C = 'C',
  D = 'D',
  E = 'E',
  F = 'F',
  G = 'G',
  H = 'H',
  I = 'I',
  J = 'J',
  L = 'L',
  M = 'M',
  N = 'N',
  O = 'O',
  P = 'P',
  R = 'R',
  S = 'S',
  U = 'U',
  W = 'W',
  X = 'X'
}

export type _Entity = Account | Address | Agency | Vehicule | VehiculeCategory;

export type _Service = {
  __typename?: '_Service';
  sdl: Scalars['String']['output'];
};

export type GetAllAccountsQueryVariables = Exact<{ [key: string]: never; }>;


export type GetAllAccountsQuery = { __typename?: 'Query', accounts?: Array<{ __typename?: 'Account', firstName: string, lastName: string, id: string, birthDate: any, createdAt?: any | null, updatedAt?: any | null, address?: { __typename?: 'Address', city: string } | null }> | null };

export type GetAccountByIdQueryVariables = Exact<{
  id: Scalars['ID']['input'];
}>;


export type GetAccountByIdQuery = { __typename?: 'Query', account?: { __typename?: 'Account', firstName: string, lastName: string, birthDate: any, address?: { __typename?: 'Address', street: string, complement: string, city: string, postalCode: string, region?: string | null, countryCode: string } | null } | null };

export type GetTokenQueryVariables = Exact<{
  credentials: AccountCredentials;
}>;


export type GetTokenQuery = { __typename?: 'Query', token?: string | null };

export type GetMeQueryVariables = Exact<{ [key: string]: never; }>;


export type GetMeQuery = { __typename?: 'Query', me?: { __typename?: 'Account', id: string, role: Role, firstName: string, lastName: string } | null };

export type RegisterCustomerMutationVariables = Exact<{
  email: Scalars['String']['input'];
  password: Scalars['String']['input'];
  title: Title;
  lastName: Scalars['String']['input'];
  firstName: Scalars['String']['input'];
  birthDate: Scalars['Date']['input'];
  address: NewAddressInput;
}>;


export type RegisterCustomerMutation = { __typename?: 'Mutation', registerCustomer?: { __typename?: 'OperationResult', message: string } | null };

export type SendMessageMutationVariables = Exact<{
  from: Scalars['ID']['input'];
  to: Scalars['ID']['input'];
  content: Scalars['String']['input'];
}>;


export type SendMessageMutation = { __typename?: 'Mutation', sendLiveMessage?: { __typename?: 'LiveMessage', at?: any | null, content: string, from: { __typename?: 'Account', firstName: string }, to: { __typename?: 'Account', firstName: string } } | null };

export type MessageSubSubscriptionVariables = Exact<{
  to: Scalars['ID']['input'];
}>;


export type MessageSubSubscription = { __typename?: 'Subscription', newLiveMessage?: { __typename?: 'LiveMessage', at?: any | null, content: string, from: { __typename?: 'Account', firstName: string }, to: { __typename?: 'Account', firstName: string } } | null };

export const GetAllAccountsDocument = gql`
    query GetAllAccounts {
  accounts {
    address {
      city
    }
    firstName
    lastName
    id
    birthDate
    createdAt
    updatedAt
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class GetAllAccountsGQL extends Apollo.Query<GetAllAccountsQuery, GetAllAccountsQueryVariables> {
    document = GetAllAccountsDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }
export const GetAccountByIdDocument = gql`
    query GetAccountById($id: ID!) {
  account(id: $id) {
    address {
      street
      complement
      city
      postalCode
      region
      countryCode
    }
    firstName
    lastName
    birthDate
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class GetAccountByIdGQL extends Apollo.Query<GetAccountByIdQuery, GetAccountByIdQueryVariables> {
    document = GetAccountByIdDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }
export const GetTokenDocument = gql`
    query GetToken($credentials: AccountCredentials!) {
  token(credentials: $credentials)
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class GetTokenGQL extends Apollo.Query<GetTokenQuery, GetTokenQueryVariables> {
    document = GetTokenDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }
export const GetMeDocument = gql`
    query GetMe {
  me {
    id
    role
    firstName
    lastName
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class GetMeGQL extends Apollo.Query<GetMeQuery, GetMeQueryVariables> {
    document = GetMeDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }
export const RegisterCustomerDocument = gql`
    mutation RegisterCustomer($email: String!, $password: String!, $title: Title!, $lastName: String!, $firstName: String!, $birthDate: Date!, $address: NewAddressInput!) {
  registerCustomer(
    account: {title: $title, firstName: $firstName, lastName: $lastName, email: $email, password: $password, birthDate: $birthDate, address: $address}
  ) {
    message
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class RegisterCustomerGQL extends Apollo.Mutation<RegisterCustomerMutation, RegisterCustomerMutationVariables> {
    document = RegisterCustomerDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }
export const SendMessageDocument = gql`
    mutation SendMessage($from: ID!, $to: ID!, $content: String!) {
  sendLiveMessage(message: {fromUserId: $from, content: $content, toUserId: $to}) {
    at
    content
    from {
      firstName
    }
    to {
      firstName
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class SendMessageGQL extends Apollo.Mutation<SendMessageMutation, SendMessageMutationVariables> {
    document = SendMessageDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }
export const MessageSubDocument = gql`
    subscription MessageSub($to: ID!) {
  newLiveMessage(toUserId: $to) {
    at
    content
    from {
      firstName
    }
    to {
      firstName
    }
  }
}
    `;

  @Injectable({
    providedIn: 'root'
  })
  export class MessageSubGQL extends Apollo.Subscription<MessageSubSubscription, MessageSubSubscriptionVariables> {
    document = MessageSubDocument;
    
    constructor(apollo: Apollo.Apollo) {
      super(apollo);
    }
  }